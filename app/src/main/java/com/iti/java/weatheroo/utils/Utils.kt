package com.iti.java.weatheroo.utils

import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.iti.java.weatheroo.R
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Constants {
    companion object Constants{
        const val ICON_BASE_URL      = "http://openweathermap.org/img/w/"
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
    }
}



class Utils {
   companion object Utils {

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
           val unit = sharedPreference.getString(Constants.Measure_Unit,"en")!!
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




       fun isConnectedToInternet(context: Context): Boolean {
           val connectivityManager =
               context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               val capabilities =
                   connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
               if (capabilities != null) {
                   when {
                       capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                           return true
                       }
                       capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                           return true
                       }
                       capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                           return true
                       }
                   }
               }
           } else {
               val activeNetworkInfo = connectivityManager.activeNetworkInfo
               if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                   return true
               }
           }
           return false
       }

       fun getDateMillis(date: String, language: String): Long {
           val f = SimpleDateFormat("dd/MM/yyyy", Locale(language))
           val d: Date = f.parse(date)
           return d.time
       }

       fun convertLongToDayDate(time: Long, language: String): String {
           val date = Date(time)
           val format = SimpleDateFormat("d MMM, yyyy", Locale(language))
           return format.format(date)
       }


       fun convertCalenderToDayString(calendar: Calendar, language: String): String {
           return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale(language))
       }


       fun getSharedPreferences(context: Context): SharedPreferences {
           return context.getSharedPreferences(
               context.getString(R.string.shared_pref),
               Context.MODE_PRIVATE
           )
       }

//       fun isSharedPreferencesLocationAndTimeZoneNull(context: Context): Boolean {
//           val myPref = getSharedPreferences(context)
//           val location = myPref.getString(context.getString(R.string.location), null)
//           val timeZone = myPref.getString(context.getString(R.string.timeZone), null)
//           return location.isNullOrEmpty() && timeZone.isNullOrEmpty()
//       }

       fun isSharedPreferencesLatAndLongNull(context: Context): Boolean {
           val myPref = getSharedPreferences(context)
           val lat = myPref.getFloat(context.getString(R.string.lat), 0.0f)
           val long = myPref.getFloat(context.getString(R.string.lon), 0.0f)
           return lat == 0.0f && long == 0.0f
       }

       fun updateSharedPreferences(
           context: Context,
           lat: Double,
           long: Double,
           location: String,
           timeZone: String
       ) {
           val editor = getSharedPreferences(context).edit()
           editor.putFloat(context.getString(R.string.lat), lat.toFloat())
           editor.putFloat(context.getString(R.string.lon), long.toFloat())
           editor.putString(context.getString(R.string.location), location)
           editor.putString(context.getString(R.string.timeZone), timeZone)
           editor.apply()
       }



       fun getCurrentLocale(context: Context): Locale? {
           return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
               context.resources.configuration.locales[0]
           } else {
               context.resources.configuration.locale
           }
       }

//       fun getCityText(context: Context, lat: Double, lon: Double, language: String): String {
//           var city = "Unknown!"
//           val geocoder = Geocoder(context, Locale(language))
//           try {
//               val addresses = geocoder.getFromLocation(lat, lon, 1)
//               if (addresses.isNotEmpty()) {
//                   city = "${addresses[0].adminArea}, ${addresses[0].countryName}"
//               }
//           } catch (e: IOException) {
//               e.printStackTrace()
//           }
//           return city
//       }

//
//       fun convertNumbersToArabic(value: Double): String {
//           return (value.toString() + "")
//               .replace("1".toRegex(), "١")
//               .replace("2".toRegex(), "٢")
//               .replace("3".toRegex(), "٣")
//               .replace("4".toRegex(), "٤")
//               .replace("5".toRegex(), "٥")
//               .replace("6".toRegex(), "٦")
//               .replace("7".toRegex(), "٧")
//               .replace("8".toRegex(), "٨")
//               .replace("9".toRegex(), "٩")
//               .replace("0".toRegex(), "٠")
//       }

//       fun convertNumbersToArabic(value: Int): String {
//           return (value.toString() + "")
//               .replace("1".toRegex(), "١").replace("2".toRegex(), "٢")
//               .replace("3".toRegex(), "٣").replace("4".toRegex(), "٤")
//               .replace("5".toRegex(), "٥").replace("6".toRegex(), "٦")
//               .replace("7".toRegex(), "٧").replace("8".toRegex(), "٨")
//               .replace("9".toRegex(), "٩").replace("0".toRegex(), "٠")
//       }


//class Utils {
//    companion object Utils{
//        fun getShortDate(ts:Long?):String{
//            if(ts == null) return ""
//            //Get instance of calendar
//            val calendar = Calendar.getInstance(Locale.getDefault())
//            //get current date from ts
//            calendar.timeInMillis = ts
//            //return formatted date
//            return android.text.format.DateFormat.format("E, dd MMM yyyy", calendar).toString()
//        }
//
//         fun getDateTime(s: String): String? {
//            try {
//                val sdf = SimpleDateFormat("HH:mm")
//                val netDate =  Date(s.toLong() * 1000)
//                return sdf.format(netDate)
//            } catch (e: Exception) {
//                return e.toString()
//            }
//        }
//
//    }
//
//}
   }
}

/*
    companion object {

        private var INSTANCE: SharedPrefsHelper? = null

        fun getInstance(context: Context): SharedPrefsHelper? =
            INSTANCE ?: synchronized(this) {
                if(INSTANCE == null){
                    return@synchronized SharedPrefsHelper()
                }else {
                    return@synchronized INSTANCE
                }
            }


        fun setLanguage(context: Context , lang : String){
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("lang",lang)
            editor.apply()
        }


        fun setTempUnit(context: Context , tempUnit : String){
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("tempUnit",tempUnit)
            editor.apply()
        }


        fun getLongitude(context: Context) : String{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getString("long","31.2885")!!
        }

        fun getTempUnit(context: Context) : String{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getString("tempUnit","metric")!!
        }

        fun getLatitude(context: Context) : String{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getString("lat","29.9604")!!
        }

        fun getLang(context: Context) : String{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getString("lang","en")!!
        }

        fun setIsFav(context: Context , isFav : Boolean){
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putBoolean("isFav", isFav)
            editor.apply()
        }

        fun getIsFav(context: Context) : Boolean{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getBoolean("isFav", false)!!
        }

        fun setIsFirstTime(context: Context , isFirstTime : Boolean){
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putBoolean("isFirstTime", isFirstTime)
            editor.apply()
        }


        fun getIsFirstTime(context: Context) : Boolean{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getBoolean("isFirstTime",true)
        }

        fun setPreviousLatLng(context: Context , latlng : String){
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            var editor = sharedPreference.edit()
            editor.putString("prevLatLng",latlng)
            editor.apply()
        }


        fun getPreviousLatLng(context: Context) : String{
            val sharedPreference = context.getSharedPreferences("myAppSHaredPrefs", Context.MODE_PRIVATE)
            return sharedPreference.getString("prevLatLng","")!!
        }


    }
}
 */