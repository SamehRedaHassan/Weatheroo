package com.iti.java.weatheroo.model.room

import android.content.Context
import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.database_layer.DatabaseLayer
import com.iti.java.weatheroo.utils.FavouritesDao
import java.util.*

class LocalSourceImpl(context : Context) : LocalSource {

    //vars
    private var favDao: FavouritesDao? = null
    // private var homeWeatherObjDao : HomeWeatherDao? = null
    // private var alarmDao : AlarmDao?
    //  override val allStoredAlarms: LiveData<List<Alarm>>


    init {
        val db: DatabaseLayer = DatabaseLayer.getDBInstance(context)
        favDao = db.favouriteDao()
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


}