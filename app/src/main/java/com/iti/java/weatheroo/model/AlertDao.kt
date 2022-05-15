package com.iti.java.weatheroo.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface AlertDao {
//    @get:Query("SELECT * FROM Alarm")
//    val getAllAlarms: LiveData<List<Alarm>>

    @Query("SELECT * FROM Alert")
    fun getAllAlarms() : LiveData<List<Alert>>

    @Delete
    fun deleteAlarm(alert: Alert?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(alert: Alert?)

//    @Update
//    fun updateAlarm(alarm: Alarm?)

    @Query("SELECT * FROM Alert WHERE id LIKE :id")
    fun getAlarm(id: UUID?): Alert?
}

