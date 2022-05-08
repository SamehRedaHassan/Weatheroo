package com.iti.java.weatheroo.utils

import java.text.SimpleDateFormat
import java.util.*


class Utils {
    companion object Utils{
        fun getShortDate(ts:Long?):String{
            if(ts == null) return ""
            //Get instance of calendar
            val calendar = Calendar.getInstance(Locale.getDefault())
            //get current date from ts
            calendar.timeInMillis = ts
            //return formatted date
            return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
        }

         fun getDateTime(s: String): String? {
            try {
                val sdf = SimpleDateFormat("HH:mm")
                val netDate =  Date(s.toLong() * 1000)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

    }

}