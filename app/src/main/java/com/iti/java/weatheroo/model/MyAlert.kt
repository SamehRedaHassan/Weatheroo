package com.iti.java.weatheroo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity
class MyAlert (

    var startDate : Long ,
    var endDate : Long ,
    var alertTime: Long ,
    var typeBool : Boolean ,
    var reasonOfAlarm : String,
    ) : Serializable {

    @PrimaryKey
    @NonNull
    var id : UUID = UUID.randomUUID()

    init {
        var alertDays: List<Date?> =  getDaysBetweenDates(Date(startDate), Date(endDate))
    }


    fun getDaysBetweenDates(startdate: Date?, enddate: Date?): List<Date?> {
        val dates: MutableList<Date?> = ArrayList()
        val calendar: Calendar = GregorianCalendar()

        calendar.time = startdate
        while (calendar.time.before(enddate)) {
            val result = calendar.time
            dates.add(result)
            calendar.add(Calendar.DATE, 1)
        }
        return dates
    }



}


