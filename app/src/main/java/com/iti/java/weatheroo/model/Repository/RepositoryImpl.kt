package com.iti.java.weatheroo.model.Repository

import android.content.Context
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.model.network.RetrofitService
import retrofit2.Call
private const val APP_ID = "c22e271e9ebc0d0e0e406902c6b750ee"

class RepositoryImpl(private val context: Context,
                     private val retrofitService: RetrofitService) : Repository {

    private var repo: RepositoryImpl? = null

    fun getInstance(context: Context) : RepositoryImpl? {
        if (repo == null) {
            repo = RepositoryImpl(context,retrofitService)
        }
        return repo
    }

    override fun getWeatherForeCast(lat: Double, Lon: Double): Call<WeatherResponse> {
        return retrofitService.getWeather(lat,Lon,APP_ID,"metric","en")
    }

}