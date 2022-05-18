package com.iti.java.weatheroo.utils


import android.location.Location
import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iti.java.weatheroo.model.Alert
import com.iti.java.weatheroo.model.CurrentWeatherModel
import com.iti.java.weatheroo.model.DailyWeatherModel
import com.iti.java.weatheroo.model.WeatherStatus
import java.lang.reflect.Type
import java.util.*

class Converters {

    @TypeConverter
    fun convertDailyWeatherModelToList(value: String?): CurrentWeatherModel {
        val listType: Type = object : TypeToken<CurrentWeatherModel?>() {}.getType()
        return Gson().fromJson<CurrentWeatherModel>(value, listType)
    }

    @TypeConverter
    fun convertCurrentWeatherModelToString(list: CurrentWeatherModel?): String {
        val gson = Gson()
        return gson.toJson(list)
    }


    @TypeConverter
    fun convertCurrentWeatherModelToList(value: String?): List<CurrentWeatherModel> {
        val listType: Type = object : TypeToken<List<CurrentWeatherModel?>?>() {}.getType()
        return Gson().fromJson<List<CurrentWeatherModel>>(value, listType)
    }

    @TypeConverter
    fun convertListCurrentWeatherModelToString(list: List<CurrentWeatherModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertListOfDailyWeatherModel(value: String?): List<DailyWeatherModel> {
        val listType: Type = object : TypeToken<List<DailyWeatherModel?>?>() {}.getType()
        return Gson().fromJson<List<DailyWeatherModel>>(value, listType)
    }

    @TypeConverter
    fun convertListOfDailyWeatherModelFromListToString(list: List<DailyWeatherModel?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun convertCurrentWeatherModel(value: String?): List<WeatherStatus> {
        val listType: Type = object : TypeToken<List<WeatherStatus?>?>() {}.getType()
        return Gson().fromJson<List<WeatherStatus>>(value, listType)
    }

    @TypeConverter
    fun fromAlertToString(alertList: List<Alert>?) : String{
        val gson = Gson()
        return gson.toJson(alertList)
    }

    @TypeConverter
    fun fromStringToAlert(alertListString: String) : List<Alert>?{
        val gson = Gson()
        if(alertListString == null){
            return Collections.emptyList()
        }else{
            var list = object : TypeToken<List<Alert?>?>(){}.type
            return gson.fromJson(alertListString, list)
        }
    }

    @TypeConverter
    fun convertDailyWeatherModelFromListToString(list: List<WeatherStatus?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID): String? {
        return uuid.toString()
    }

    @TypeConverter
    fun uuidFromString(string: String?): UUID? {
        return UUID.fromString(string)
    }

    @TypeConverter
    fun fromListOfDatesToString(dateList: List<Date>?) : String{
        val gson = Gson()
        return gson.toJson(dateList)
    }

    @TypeConverter
    fun fromStringToListOfDates(dateListString: String) : List<Date>?{
        val gson = Gson()
        return run {
            var list = object : TypeToken<List<Date?>?>(){}.type
            gson.fromJson(dateListString, list)
        }
    }

}
