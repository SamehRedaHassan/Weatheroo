package com.iti.java.weatheroo.model.room

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.iti.java.weatheroo.model.FavouriteLocation
import java.util.*

interface LocalSource {
    fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>>
    fun addFavouriteLocation(location: FavouriteLocation)
    fun deleteFavouriteLocation(location: FavouriteLocation)
    fun getFavWeatherObj(id: UUID): FavouriteLocation?
    fun deleteHomeLocation()
}