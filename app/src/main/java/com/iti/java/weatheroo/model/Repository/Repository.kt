package com.iti.java.weatheroo.model.Repository

import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.Alert
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
    fun getAllAlarms() : LiveData<List<Alert>>
    fun deleteAlarm(alert: Alert?)
    fun insertAlarm(alert: Alert?)
    fun getAlarm(id: UUID?): Alert?

}