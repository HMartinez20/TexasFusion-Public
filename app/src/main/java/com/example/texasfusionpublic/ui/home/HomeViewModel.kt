/**
 * HomeViewModel.kt
 *
 * Handles business functions and calculations.
 * Parses data related to the hours of operation to dislpay on the home page.
 **/
package com.example.texasfusionpublic.ui.home

import androidx.lifecycle.ViewModel
import java.lang.StringBuilder
import java.util.*

class HomeViewModel : ViewModel() {

    // Parse data to separate days of the week
    fun sortData(data: String): String {
        val schedule = mutableMapOf<String,String>()
        data.removePrefix("{").removeSuffix("}").split(", ").forEach {
            schedule.put(it.substringBefore("="), it.substringAfter("="))
        }

        val scheduleString = StringBuilder()
        scheduleString.append(schedule.getValue("Sunday"), "\n")
        scheduleString.append(schedule.getValue("Monday"), "\n")
        scheduleString.append(schedule.getValue("Tuesday"), "\n")
        scheduleString.append(schedule.getValue("Wednesday"), "\n")
        scheduleString.append(schedule.getValue("Thursday"), "\n")
        scheduleString.append(schedule.getValue("Friday"), "\n")
        scheduleString.append(schedule.getValue("Saturday"))
        return scheduleString.toString()
    }

    // Retrieve the system's current day of the week
    fun getTodaysSchedule(data: String): String{
        val dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        var day: String = ""
        when(dayOfWeek){
            Calendar.SUNDAY -> day = "Sunday"
            Calendar.MONDAY -> day = "Monday"
            Calendar.TUESDAY -> day = "Tuesday"
            Calendar.WEDNESDAY -> day = "Wednesday"
            Calendar.THURSDAY -> day = "Thursday"
            Calendar.FRIDAY -> day = "Friday"
            Calendar.SATURDAY -> day = "Saturday"
        }

        data.removePrefix("{").removeSuffix("}").split(", ").forEach {
            if(it.contains(day))
                return it.substringAfter("=")
        }
        return "00:00 - 00:00"
    }
}