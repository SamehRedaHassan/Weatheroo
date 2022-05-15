package com.iti.java.weatheroo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

@Entity
class Alert (

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






//    private fun stringToDate(aDate: String?, aFormat: String): Date? {
//        if (aDate == null) return null
//        val pos = ParsePosition(0)
//        val simpleDateFormat = SimpleDateFormat(aFormat)
//        return simpleDateFormat.parse(aDate, pos)
//    }
//
//    // Wind , Rain , Snow , Cloud , ThunderStorm , MistOrFog
//    private fun getReasonOfTheAlarm(reasonOfAlarm : String) : ReasonOfTheAlarm{
//        when (reasonOfAlarm) {
//            "Wind" -> {
//                return ReasonOfTheAlarm.Wind
//            }
//            "Rain" -> {
//                return ReasonOfTheAlarm.Rain
//            }
//            "Snow" -> {
//                return ReasonOfTheAlarm.Snow
//            }
//            "Cloud" -> {
//                return ReasonOfTheAlarm.Cloud
//            }
//            "Thunder Storm" -> {
//                return ReasonOfTheAlarm.ThunderStorm
//            }
//            "Mist/Fog" -> {
//                return ReasonOfTheAlarm.MistOrFog
//            }
//            else -> {
//                return ReasonOfTheAlarm.Wind
//            }
//        }
//    }


//    private fun stringToTime(aTime: String?): CustomTime? {
//        if (aTime == null) return null
//        val separated = aTime.split(":").toTypedArray()
//        val hour = separated[0].toInt()
//        val secondPart = separated[1]
//        val separateTheAmAndPm = secondPart.toString().split(" ").toTypedArray()
//        val minutes = separateTheAmAndPm[0].toInt()
//        val amOrPm = separateTheAmAndPm[1]
//        return CustomTime(hour, minutes, amOrPm)
//    }

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

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(date)
    }


}


