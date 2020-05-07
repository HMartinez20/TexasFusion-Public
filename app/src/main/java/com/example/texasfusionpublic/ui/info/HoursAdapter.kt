/**
 * HoursAdapter.kt
 *
 * Handles displaying a list of items in a recycler view.
 * Displays each day of the week and the availability for that day.
 **/
package com.example.texasfusionpublic.ui.info

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.texasfusionpublic.R
import kotlinx.android.synthetic.main.hours_day.view.*

class HoursAdapter(
    private val schedule: List<HoursFragment.Schedule>,
    private val clickListener: (schedule: HoursFragment.Schedule) -> Unit
): RecyclerView.Adapter<HoursAdapter.ViewHolder>(){

    // Specifies the reusable layout to display the information
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hours_day, parent, false))

    // Used to access each object in the list
    override fun getItemCount(): Int = schedule.size

    // Specifies binding to display the item's information and function to call when clicked/tapped
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schedule[position], clickListener)
    }

    // This class corresponds to the layout specified in the onCreateViewHolder function
    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val day = item.day
        val timeStart = item.timeStart
        val timeEnd = item.timeEnd
        val btnEdit = item.btnEdit

        fun bind(schedule: HoursFragment.Schedule, clickListener: (schedule: HoursFragment.Schedule) -> Unit){
            btnEdit.setOnClickListener{
                clickListener(schedule)
            }

            day.text = schedule.dayOfWeek
            timeStart.text = "${schedule.startTime}${schedule.startAbbr.toLowerCase()}"
            timeEnd.text = "${schedule.endTime}${schedule.endAbbr.toLowerCase()}"
        }
    }

}