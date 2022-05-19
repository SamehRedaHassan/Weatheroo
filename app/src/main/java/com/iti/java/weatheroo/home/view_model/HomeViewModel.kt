package com.iti.java.weatheroo.home.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.*
import com.iti.java.weatheroo.model.Repository.Repository
import com.iti.java.weatheroo.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class HomeViewModel(private val _irepo : Repository,val context: Context): ViewModel()  {
    lateinit var currentWeather : CurrentWeatherModel

    var hourly = MutableLiveData<List<CurrentWeatherModel>>()
    var daily = MutableLiveData<List<DailyWeatherModel>>()

    fun getCurrentWeather( )  {
        val response = _irepo.getWeatherForeCast(Utils.getCurrentLattitude(context),Utils.getCurrentLongitude(context))
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>) {
                currentWeather = response.body()!!.current
                hourly.postValue(response.body()!!.hourly)
                daily.postValue(response.body()!!.daily)
                var weatherObj = response.body()
                weatherObj?.id = "id"
                _irepo.saveCurrentWeather(weatherObj)

            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            }
        })
    }
    fun getLocalWeather() :LiveData<List<WeatherResponse>> {
        return  _irepo.getWeatherList()
    }

}



