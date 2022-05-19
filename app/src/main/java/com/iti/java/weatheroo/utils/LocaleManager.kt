package com.iti.java.weatheroo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Build.VERSION_CODES.N
import android.os.LocaleList
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.LinkedHashSet

class LocaleManager {

    companion object{
        fun setLocale(context: Context){
            setNewLocale(context, getLanguage(context))
            update(context, getLanguage(context))
        }

        fun setNewLocale(context: Context, language : String){
            persistLanguage(context, language)
            update(context, language)
        }

        fun update(context: Context, language: String){
            updateResources(context, language)
            var appContext : Context = context.applicationContext
            if(context != appContext){
                updateResources(appContext, language)
            }
        }

        fun getLanguage(context: Context) : String{
            var languageShared : SharedPreferences = context.getSharedPreferences(Constants.Shared_Preferences, AppCompatActivity.MODE_PRIVATE)
            return languageShared.getString(Constants.LANGUAGE, "en") ?: ""
        }

        fun persistLanguage(context: Context , language: String){
            Utils.saveCurrentLang(language,context)
         //   Utility.saveLanguageToSharedPref(context, Constants.LANGUAGE, language)
        }
        @SuppressLint("ObsoleteSdkInt")
        fun updateResources(context: Context, language: String){
            var locale : Locale = Locale(language)
            Locale.setDefault(locale)

            var resources : Resources = context.resources
            var config : Configuration = Configuration(resources.configuration)
            if(Build.VERSION.SDK_INT >= 17){
                config.setLocale(locale)
                //context = context.createConfigurationContext(config)
            }else{
                config.locale = locale
            }

            resources.updateConfiguration(config, resources.displayMetrics)
        }
    }
}