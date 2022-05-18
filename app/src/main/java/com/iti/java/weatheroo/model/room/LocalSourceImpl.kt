package com.iti.java.weatheroo.model.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.AlertDao
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.database_layer.DatabaseLayer
import com.iti.java.weatheroo.utils.FavouritesDao
import java.util.*

class LocalSourceImpl(context : Context) : LocalSource {

    //vars
    private var favDao: FavouritesDao?
    // private var homeWeatherObjDao : HomeWeatherDao? = null
     private var alertDao : AlertDao?
    //  override val allStoredAlarms: LiveData<List<Alarm>>


    init {
        val db: DatabaseLayer = DatabaseLayer.getDBInstance(context)
        favDao = db.favouriteDao()
        alertDao = db.alertDao()
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


}