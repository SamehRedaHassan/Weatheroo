package com.iti.java.weatheroo.model.network

import com.iti.java.weatheroo.model.WeatherResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface RetrofitService  {
    @GET("onecall")
    fun getWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") appid: String ,
        @Query("units") unit: String,
        @Query("lang") language: String,
    ): Call<WeatherResponse>




    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}