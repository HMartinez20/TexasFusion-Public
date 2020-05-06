package com.example.texasfusionpublic.ui.info

import androidx.lifecycle.ViewModel

class InfoViewModel : ViewModel(){
    fun sortData(data: String): List<HoursFragment.Schedule> {
//        val schedule = mutableMapOf<String,String>()
        val scheduleList = MutableList(7){HoursFragment.Schedule("","", "", "", "")}

        data.removePrefix("{").removeSuffix("}").split(", ").forEach {
//            schedule.put(it.substringBefore("="), it.substringAfter("="))
            var dayOfWeek = 0
            when(it.substringBefore("=")) {
               "Sunday" -> dayOfWeek = 0
                "Monday" -> dayOfWeek = 1
                "Tuesday" -> dayOfWeek = 2
                "Wednesday" -> dayOfWeek = 3
                "Thursday" -> dayOfWeek = 4
                "Friday" -> dayOfWeek = 5
                "Saturday" -> dayOfWeek = 6
            }
            scheduleList.set(dayOfWeek,
                HoursFragment.Schedule(
                    dayOfWeek = it.substringBefore("="),
                    startTime = it.substringAfter("=").substringBefore("-").filter { !it.isLetter() }.trim(),
                    startAbbr = it.substringAfter("=").substringBefore("-").filter { it.isLetter() }.trim(),
                    endTime = it.substringAfter("=").substringAfter("-").filter { !it.isLetter() }.trim(),
                    endAbbr = it.substringAfter("=").substringAfter("-").filter { it.isLetter() }.trim()
                )
            )
        }
        return scheduleList
    }
}