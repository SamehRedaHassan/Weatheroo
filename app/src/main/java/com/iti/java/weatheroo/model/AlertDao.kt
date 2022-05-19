package com.iti.java.weatheroo.model

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

@Dao
interface AlertDao {


    @Query("SELECT * FROM MyAlert")
    fun getAllAlarms() : LiveData<List<MyAlert>>

    @Delete
    fun deleteAlarm(myAlert: MyAlert?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlarm(myAlert: MyAlert?)

    @Query("SELECT * FROM MyAlert WHERE id LIKE :id")
    fun getAlarm(id: UUID?): MyAlert?
}

