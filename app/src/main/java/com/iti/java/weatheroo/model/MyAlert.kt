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

}


