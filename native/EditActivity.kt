package com.example.lab2_mobile_native

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class EditActivity: AppCompatActivity() {
    private lateinit var initialActivity: Activity;
    private lateinit var editTextDate: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextTime: EditText
    private lateinit var editTextDuration: EditText
    lateinit var editButton: Button;
    lateinit var id: Number;
    private lateinit var idText: EditText
    lateinit var priorityOptions: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        editTextDate = findViewById(R.id.dateEditText)
        editTextTitle=findViewById(R.id.titleEditText)
        editTextDuration=findViewById(R.id.durationEditText)
        editTextDescription=findViewById(R.id.descriptionEditText)
        editTextTime = findViewById(R.id.timeEditText)
        spinnerPriority = findViewById(R.id.prioritySpinner)
        idText=findViewById(R.id.idInputCreate)

        idText.setText(Activity.currentId.toString())
        idText.isEnabled=false


        editTextDate.setOnClickListener {
            showDatePickerDialog()
        }

        priorityOptions = arrayOf("Leisure Time", "Routine", "Personal Development", "Health", "Social", "Work", "Optional", "Family", "Critical", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityOptions)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter

        val bundle = intent.getBundleExtra("activityBundle")
        if(bundle != null) {
            val activity = bundle.getParcelable<Activity>("activity")
            if (activity != null) {
                initialActivity = activity
                id = activity.id
            }
        }
        initializeInputs()

        val editButton: Button=findViewById(R.id.editButton)

        editButton.setOnClickListener{
            editActivity()

        }
    }

    private fun showDatePickerDialog() {

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->

            val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear) // Month is 0-based
            editTextDate.setText(formattedDate)
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun editActivity() {
        if (checkInputs()) {


            initialActivity.title=editTextTitle.text.toString()
            initialActivity.date=editTextDate.text.toString()
            initialActivity.time=editTextTime.text.toString()
            initialActivity.duration=editTextDuration.text.toString()
            initialActivity.description=editTextDescription.text.toString()
            initialActivity.priority=spinnerPriority.selectedItem.toString()


            val bundle = Bundle()
            bundle.putParcelable("activity", initialActivity);
            intent.putExtra("activityBundle", bundle)
            intent.putExtra("id", id)
            setResult(RESULT_OK, intent)
            finish()
        } else {
            Toast.makeText(
                this,
                "All fields must be completed!",
                Toast.LENGTH_LONG
            ).show();
        }
    }
    private fun checkInputs(): Boolean {
        if (editTextTitle.text.isEmpty() or editTextDate.text.isEmpty() or editTextTime.text.isEmpty() or editTextDuration.text.isEmpty() or spinnerPriority.toString().isEmpty()) {
            return false
        }

        return true
    }
    private fun goBack() {
        intent = Intent()
        finish()
    }
    private fun initializeInputs(){

        idText.setText(id.toString());
        idText.isEnabled = false
        editTextTitle.setText(initialActivity.title)
        editTextDate.setText(initialActivity.date)
        editTextTime.setText(initialActivity.time)
        editTextDuration.setText(initialActivity.duration)
        editTextDescription.setText(initialActivity.description)
        val priorityPosition = priorityOptions.indexOf(initialActivity.priority)
        spinnerPriority.setSelection(priorityPosition)

    }
}