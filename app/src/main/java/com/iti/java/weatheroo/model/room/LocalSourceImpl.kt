package com.iti.java.weatheroo.model.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.*
import com.iti.java.weatheroo.model.database_layer.DatabaseLayer
import com.iti.java.weatheroo.utils.FavouritesDao
import java.util.*

class LocalSourceImpl(context : Context) : LocalSource {

    //vars
     private var favDao: FavouritesDao?
     private var weatherDao : WeatherDao?
     private var alertDao : AlertDao?
     // override val allStoredAlarms: LiveData<List<Alert>>


    init {
        val db: DatabaseLayer = DatabaseLayer.getDBInstance(context)
        favDao = db.favouriteDao()
        alertDao = db.alertDao()
        weatherDao = db.weatherDao()

    }

    override fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>> {
       return favDao!!.getAllFavouriteLocations()
    }

    override fun addFavouriteLocation(location: FavouriteLocation) {
        favDao?.insertFavouriteLocation(location)
    }

    override fun deleteFavouriteLocation(location: FavouriteLocation) {
       favDao?.deleteFavouriteLocation(location)
    }

    override fun getFavWeatherObj(id: UUID): FavouriteLocation? {
       return favDao?.getFavWeatherObj(id)
    }

    override fun deleteHomeLocation() {
        favDao?.deleteHomeLocation()
    }

    override fun getAllAlarms(): LiveData<List<MyAlert>> {
      return  alertDao!!.getAllAlarms()
    }

    override fun deleteAlarm(myAlert: MyAlert?) {
        alertDao?.deleteAlarm(myAlert)
    }

    override fun insertAlarm(myAlert: MyAlert?) {
        alertDao?.insertAlarm(myAlert)

    }

    override fun getAlarm(id: UUID?): MyAlert? {
       return alertDao?.getAlarm(id)
    }

    override fun getWeatherList(): LiveData<List<WeatherResponse>> {
        return weatherDao?.getWeatherList()!!
    }

    override fun deleteCurrentWeather(weatherObj: WeatherResponse?) {
        weatherDao?.deleteCurrentWeather(weatherObj)
    }

    override fun saveCurrentWeather(weatherObj: WeatherResponse?) {
       weatherDao?.saveCurrentWeather(weatherObj)
    }


}