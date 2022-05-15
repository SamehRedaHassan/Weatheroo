package com.iti.java.weatheroo.model.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.iti.java.weatheroo.model.Alert
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSource
import retrofit2.Call
import java.util.*

private const val APP_ID = "c22e271e9ebc0d0e0e406902c6b750ee"

class RepositoryImpl(private val context: Context,
                     private val retrofitService: RetrofitService,
                     var localSource: LocalSource
) : Repository {

    private var repo: RepositoryImpl? = null

    fun getInstance(context: Context) : RepositoryImpl? {
        if (repo == null) {
            repo = RepositoryImpl(context,retrofitService,localSource)
        }
        return repo
    }

    override fun getWeatherForeCast(lat: Double, Lon: Double): Call<WeatherResponse> {
        return retrofitService.getWeather(lat,Lon,APP_ID,"metric","en")
    }

    override fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>> {
        return localSource.getAllFavouriteLocations()
    }

    override fun addFavouriteLocation(location: FavouriteLocation) {
        localSource.addFavouriteLocation(location)
    }

    override fun deleteFavouriteLocation(location: FavouriteLocation) {
        localSource.deleteFavouriteLocation(location)
    }

    override fun getFavWeatherObj(id: UUID): FavouriteLocation? {
      return  localSource.getFavWeatherObj(id)
    }



    override fun deleteHomeLocation() {
        localSource.deleteHomeLocation()
    }

    override fun getAllAlarms(): LiveData<List<Alert>> {
       return localSource.getAllAlarms()
    }

    override fun deleteAlarm(alert: Alert?) {
        localSource.deleteAlarm(alert)
    }

    override fun insertAlarm(alert: Alert?) {
        localSource.insertAlarm(alert)
    }

    override fun getAlarm(id: UUID?): Alert? {
        return localSource.getAlarm(id)
    }

}