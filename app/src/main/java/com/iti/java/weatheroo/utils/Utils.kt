package com.iti.java.weatheroo.utils

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import com.iti.java.weatheroo.R
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Constants {
    companion object Constants{
        const val ICON_BASE_URL      = "https://openweathermap.org/img/w/"
        const val PNG                = ".png"
        const val Shared_Preferences = "WeatherSharedPreferences"
        const val LATITUDE           = "LATITUDE"
        const val LONGITUDE          = "LONGITUDE"
        const val SOURCE_FRAGMENT    = "SOURCE"
        const val FAVOURITES         = "FAVOURITES"
        const val SETTINGS           = "SETTINGS"
        const val SPLASH             = "SPLASH"
        const val CHOICE_FRAGMENT    = "CHOICE_FRAGMENT"
        const val MY_DATA_BASE       = "MY_DATA_BASE"
        const val Measure_Unit       = "Temperature_Unit"
        const val LANGUAGE           = "lang"
        const val IMPERIAL           = "IMPERIAL"
        const val STANDARD           = "STANDARD"
        const val METRIC             = "METRIC"
        const val ENGLISH_LANGUAGE   = "en"
        const val Arabic_LANGUAGE    = "ar"
        const val IS_USING_GPS       = "IS_USING_GPS"
        const val IS_FIRST_LAUNCH    = "IS_FIRST_LAUNCH"
        const val UUID_KEY           = "UUID_KEY"
    }
}



class Utils {
   companion object Utils {


       fun getIsFirstTimeForAddAlarm(context: Context) : Boolean{
           val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
           return sharedPreference.getBoolean("isFirstTimeAdd",true)
       }

       fun setIsFirstTimeAddAlarm(context: Context , isFirstTime : Boolean){
           val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putBoolean("isFirstTimeAdd", isFirstTime)
           editor.apply()
       }



       fun saveCurrentLang(language: String , context :Context){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putString(Constants.LANGUAGE,language)
           editor.apply()
       }

       fun getCurrentLang(context :Context):String{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getString(Constants.LANGUAGE,"en")!!
       }

       fun getCurrentUnit(context :Context):String{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getString(Constants.Measure_Unit,Constants.METRIC)!!
       }

       fun setIsFirstLaunch(context :Context , isFirstLaunch : Boolean){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putBoolean(Constants.IS_FIRST_LAUNCH,isFirstLaunch)
           editor.apply()
       }

       fun getIsFirstLaunch(context :Context ):Boolean{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getBoolean(Constants.IS_FIRST_LAUNCH, true)
       }

       fun setCurrentUnit(context :Context , unit : String){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putString(Constants.Measure_Unit,unit)
           editor.apply()
       }


       fun getISUsingGPS(context :Context):Boolean{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getBoolean(Constants.IS_USING_GPS,true)
       }

       fun setUsingGPS(context :Context , isUsingGPS : Boolean){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putBoolean(Constants.IS_USING_GPS,isUsingGPS)
           editor.apply()
       }




       fun getCurrentTemperatureUnit(context :Context):String{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           val unit = sharedPreference.getString(Constants.Measure_Unit,"METRIC")!!
           when (unit) {
                Constants.METRIC->{
                    return "°C"
                }
               Constants.STANDARD->{
                   return "°K"
               }
               Constants.IMPERIAL->{
                   return "°F"
               }

           }
           return "°C"
       }

       fun getCurrentWindUnit(context :Context):String{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           val unit = sharedPreference.getString(Constants.Measure_Unit,"en")!!
           when (unit) {
               Constants.METRIC->{
                   return "m/s"
               }
               Constants.STANDARD->{
                   return "m/s"
               }
               Constants.IMPERIAL->{

                   return "m/h"
               }

           }
             return "m/s"
       }

       fun getCurrentHumidityUnit():String{
           return  " g.m"
       }



       fun getCurrentPressureUnit():String{
           return " mBar"
       }

       fun getDateTime(time: Long, language: String): String {
           val date = Date(TimeUnit.SECONDS.toMillis(time))
           val format = SimpleDateFormat("h:mm a", Locale(language))
           return format.format(date)
       }

       fun getDayOfWeek(timestamp: Long): String {
           return SimpleDateFormat("EEEE", Locale.ENGLISH).format(timestamp * 1000)
       }



       fun getCurrentCityFromLatLon(context: Context , lat : Double ,lon : Double ) : String{
           var addressGeocoder : Geocoder = Geocoder(context, Locale.getDefault())
           try {
               var myAddress : List<Address> = addressGeocoder.getFromLocation(lat, lon, 2)
               if(myAddress.isNotEmpty()){
                   return "${myAddress[0].adminArea}, ${myAddress[0].countryName}"
                   Log.i("NADAAAAAAA", "getAddressForLocation: ${myAddress[0].subAdminArea} ${myAddress[0].adminArea}")
               }else{
                   return  "no"
               }
           }catch (e : IOException){
               e.printStackTrace()
               return  "ca"

           }
       }




       fun getCurrentLattitude(context: Context):Double{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getString(Constants.LATITUDE,"31.2878")!!.toDouble()
       }
       fun setCurrentLatitude(context: Context , latitude : String){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putString(Constants.LATITUDE,latitude)
           editor.apply()
       }

       fun getCurrentLongitude(context: Context):Double{
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           return sharedPreference.getString(Constants.LONGITUDE,"31.003141776331283")!!.toDouble()
       }

       fun setCurrentLongitude(context: Context , longitude : String){
           val sharedPreference = context.getSharedPreferences(Constants.Shared_Preferences, Context.MODE_PRIVATE)
           var editor = sharedPreference.edit()
           editor.putString(Constants.LONGITUDE,longitude)
           editor.apply()
       }



       fun longToDateAsString(dateInMillis: Long, language: String): String {
           val d = Date(dateInMillis)
           val dateFormat: DateFormat = SimpleDateFormat("d MMM, yyyy",  Locale(language))
           return dateFormat.format(d)
       }

        fun showNoConnectivitySnackbar(view: View, context: Context ) {
           val snack = Snackbar.make(view, "No Network", Snackbar.LENGTH_LONG) // replace root view with your view Id
           snack.setAction("enable wifi") {
               startActivity(context,Intent(Settings.ACTION_WIFI_SETTINGS),null)
           }
           snack.show()
       }

       fun getDayOfWeek(timestamp: Long, language: String): String {
           return SimpleDateFormat("EEEE",  Locale(language)).format(timestamp * 1000)
       }

   }
}