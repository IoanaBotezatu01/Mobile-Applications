package com.example.lab2_mobile_native

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActivityAdapter(
    private val activities: MutableList<Activity>,
    private val context: ListActivities
) : RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>() {

    class ActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textViewTitle)
        val date: TextView = itemView.findViewById(R.id.editTextDate)
        val time: TextView = itemView.findViewById(R.id.editTextTime)
        val duration: TextView = itemView.findViewById(R.id.textViewDuration)
        val description: TextView = itemView.findViewById(R.id.editTextTextMultiLineDescription)
        val priority: TextView = itemView.findViewById(R.id.textViewPriority)
        val editButton: Button = itemView.findViewById(R.id.edit)
        val deleteButton: Button = itemView.findViewById(R.id.delete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_activity, parent, false)
        return ActivityViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActivityViewHolder, position: Int) {
        val activityItem = activities[position]
        holder.title.text = activityItem.title
        holder.date.text = activityItem.date
        holder.time.text = activityItem.time
        holder.duration.text = activityItem.duration
        holder.description.text = activityItem.description
        holder.priority.text = activityItem.priority


        holder.editButton.setOnClickListener {
            val bundle = Bundle();
            val intent = Intent(context, EditActivity::class.java)

            bundle.putParcelable("activity", activities[position]);
            intent.putExtra("activityBundle", bundle);
            context.startActivityForResult(intent, 5)
        }
        holder.deleteButton.setOnClickListener{

            val dialog = Dialog(context)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.delete_activity)




            val yesView = dialog.findViewById(R.id.buttonDelete) as View

            val noView = dialog.findViewById(R.id.buttonCancel) as View

            yesView.setOnClickListener {
                activities.removeAt(position)
                notifyDataSetChanged()
                dialog.dismiss()
            }

            noView.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }
    }

    override fun getItemCount(): Int = activities.size
}
