package com.iti.java.weatheroo.model
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WeatherDao {
    @Query("SELECT * FROM WeatherResponse")
    fun getWeatherList(): LiveData<List<WeatherResponse>>

    @Delete
    fun deleteCurrentWeather(weatherObj: WeatherResponse?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCurrentWeather(weatherObj: WeatherResponse?)

//    @Query("DELETE FROM weatherresponse WHERE isFav = 0")
//    fun deleteHomeWeatherObjByLatLongId()


//    @Update
//    fun updateHomeWeatherObj(weatherObj: WeatherResponse?)

//
//    @Query("SELECT * FROM weatherresponse WHERE loc = :location")
//    fun getHomeWeatherObj(location: String?): LiveData<WeatherResponse>
}