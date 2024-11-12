package com.example.lab2_mobile_native

import android.widget.ArrayAdapter
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.service.credentials.CreateEntry
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.get
import java.sql.Time
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var editTextDate: EditText
    private lateinit var spinnerPriority: Spinner
    private lateinit var editTextTitle: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var editTextTime: EditText
    private lateinit var editTextDuration: EditText
    lateinit var saveButton: Button;
    lateinit var id: String;
    private lateinit var idText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_activity)

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

        val priorityOptions = arrayOf("Leisure Time", "Routine", "Personal Development", "Health", "Social", "Work", "Optional", "Family", "Critical", "Other")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityOptions)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPriority.adapter = adapter


        val saveButton: Button=findViewById(R.id.saveButton)

        saveButton.setOnClickListener{
            saveActivity()

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

    private fun saveActivity() {
        if (checkInputs()) {


            val activity = Activity(
                editTextTitle.text.toString(),
                editTextDate.text.toString(),
                editTextTime.text.toString(),
                editTextDuration.text.toString(),
                editTextDescription.text.toString(),
                spinnerPriority.selectedItem.toString()
            )

            val bundle = Bundle()
            bundle.putParcelable("activity", activity);
            intent.putExtra("activityBundle", bundle)
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

}
