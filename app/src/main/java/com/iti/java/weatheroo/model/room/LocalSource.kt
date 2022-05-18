package com.iti.java.weatheroo.model.room

import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.FavouriteLocation
import java.util.*

interface LocalSource {
    fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>>
    fun addFavouriteLocation(location: FavouriteLocation)
    fun deleteFavouriteLocation(location: FavouriteLocation)
    fun getFavWeatherObj(id: UUID): FavouriteLocation?
    fun deleteHomeLocation()
    fun getAllAlarms() : LiveData<List<MyAlert>>
    fun deleteAlarm(myAlert: MyAlert?)
    fun insertAlarm(myAlert: MyAlert?)
    fun getAlarm(id: UUID?): MyAlert?
}