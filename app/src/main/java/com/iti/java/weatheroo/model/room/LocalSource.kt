package com.iti.java.weatheroo.model.room

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.WeatherResponse
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
    fun getWeatherList(): LiveData<List<WeatherResponse>>
    fun deleteCurrentWeather(weatherObj: WeatherResponse?)
    fun saveCurrentWeather(weatherObj: WeatherResponse?)
}