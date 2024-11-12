package com.example.lab2_mobile_native

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.format.DateTimeFormatter

class ListActivities : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ActivityAdapter

    private var activities: MutableList<Activity> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activities)
        initActivities()
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        adapter = ActivityAdapter(
            activities,
            context = this
        )
        recyclerView.adapter = adapter


        val fabAddActivity: FloatingActionButton = findViewById(R.id.fabAddActivity)
        fabAddActivity.setOnClickListener {
            val intent = Intent(this@ListActivities, AddActivity::class.java)
            startActivityForResult(intent,3)
        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 3) {
            Log.i(data.toString()," ")
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    val bundle = data.getBundleExtra("activityBundle")
                    val activity = bundle?.getParcelable<Activity>("activity")
                    if (activity != null) {
                        addActivityToList(activity)
                    }
                }
                Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()
                recyclerView.adapter?.notifyItemInserted(activities.size - 1)
            }
        } else if(requestCode == 5){
            if(resultCode == android.app.Activity.RESULT_OK){
                if(data != null){
                    val bundle = data.getBundleExtra("activityBundle")
                    val activity = bundle?.getParcelable<Activity>("activity")
                    val id = data.getIntExtra("id", -1)
                    if(activity != null && id != -1){
                        updateActivity(activity, id)
                    }
                }
            }
        }
    }
    private fun addActivityToList(activity: Activity) {
        activities.add(activity);
    }
    private fun updateActivity(activity: Activity, id: Number) {
        for(i in 0 until activities.size){
            if(activities[i].id == id){
                activities[i] = activity
                Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show()
                recyclerView.adapter?.notifyItemChanged(i)
            }
        }
    }
    fun getId(): Number{
        return activities.size + 1;
    }


    fun initActivities()
    {
        val activity1= Activity("Activity 1", "2024-11-01", "18:00", "20 minutes", "-", "routine")
        val activity2=Activity("Activity 2", "2024-11-02", "19:00", "200 minutes", "-", "work")
        val activity3= Activity("Activity 3", "2024-11-03", "10:45", "30 minutes", "-", "health")

        activities.add(activity1)
        activities.add(activity2)
        activities.add(activity3)
    }
}

