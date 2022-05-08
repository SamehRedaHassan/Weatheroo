package com.iti.java.weatheroo.home.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.CurrentWeatherModel
import com.iti.java.weatheroo.model.DailyWeatherModel
import com.iti.java.weatheroo.model.Repository.Repository
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.model.WeatherStatus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException

class HomeViewModel(private val _irepo : Repository): ViewModel()  {
    lateinit var currentWeather : CurrentWeatherModel

    val hourly = MutableLiveData<List<CurrentWeatherModel>>()
    val daily = MutableLiveData<List<DailyWeatherModel>>()

    // var weatherResponse = WeatherResponse(0.0,0.0,"",0, CurrentWeatherModel(0,0.0,0.0,0,0,0,0.0,MutableLiveData<List<WeatherStatus>>(),0,0.0,0.0,0,0,0,0.0,0),MutableLiveData<List<CurrentWeatherModel>>(),MutableLiveData<List<DailyWeatherModel>>())
    fun getCurrentWeather()  {
        val response = _irepo.getWeatherForeCast(30.560930801547254,31.003141776331283)
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>) {
                currentWeather = response.body()!!.current
                hourly.postValue(response.body()!!.hourly)
                daily.postValue(response.body()!!.daily)
                Log.i("QQQQQQQQ", "onResponse: " + response.body())

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("FFFFFF", "onResponse: " )
            }

        })
    }

}



class HomeViewModelFactory(private val _irepo : Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(_irepo) as T
        }else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}