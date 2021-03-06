package com.iti.java.weatheroo.model.database_layer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.iti.java.weatheroo.model.*
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Converters
import com.iti.java.weatheroo.utils.FavouritesDao

@Database(entities = [ FavouriteLocation::class , MyAlert::class, WeatherResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DatabaseLayer : RoomDatabase() {

    abstract fun favouriteDao(): FavouritesDao?
    abstract fun alertDao(): AlertDao?
    abstract fun weatherDao(): WeatherDao?

    companion object{
        private var INSTANCE: DatabaseLayer? = null

        @Synchronized
        fun getDBInstance(context: Context): DatabaseLayer {
            return INSTANCE ?: Room.databaseBuilder(context.applicationContext, DatabaseLayer::class.java , Constants.MY_DATA_BASE).allowMainThreadQueries().build()
        }
    }
}