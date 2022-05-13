package com.iti.java.weatheroo.model.Repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.WeatherResponse
import retrofit2.Call
import java.util.*

interface Repository {
    fun getWeatherForeCast(lat : Double , Lon : Double): Call<WeatherResponse>
    fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>>
    fun addFavouriteLocation(location: FavouriteLocation)
    fun deleteFavouriteLocation(location: FavouriteLocation)
    fun getFavWeatherObj(id: UUID): FavouriteLocation?
    fun deleteHomeLocation()

}