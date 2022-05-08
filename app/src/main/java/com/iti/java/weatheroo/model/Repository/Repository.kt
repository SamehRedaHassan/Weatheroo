package com.iti.java.weatheroo.model.Repository

import com.iti.java.weatheroo.model.WeatherResponse
import retrofit2.Call

interface Repository {
    fun getWeatherForeCast(lat : Double , Lon : Double): Call<WeatherResponse>
}