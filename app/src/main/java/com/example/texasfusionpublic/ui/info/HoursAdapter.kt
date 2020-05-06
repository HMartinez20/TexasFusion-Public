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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hours_day, parent, false))

    override fun getItemCount(): Int = schedule.size //days.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schedule[position], clickListener)
    }

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