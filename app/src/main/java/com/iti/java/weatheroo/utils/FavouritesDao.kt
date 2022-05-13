package com.iti.java.weatheroo.utils

import androidx.lifecycle.LiveData
import androidx.room.*
import com.iti.java.weatheroo.model.FavouriteLocation
import java.util.*

@Dao
interface FavouritesDao {
    @Query("SELECT * FROM FavouriteLocation")
    fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavouriteLocation(location: FavouriteLocation)

    @Delete
    fun deleteFavouriteLocation(location: FavouriteLocation)


    @Query("SELECT * FROM FavouriteLocation WHERE id LIKE :id")
    fun getFavWeatherObj(id: UUID): FavouriteLocation?

    @Query("DELETE FROM FavouriteLocation WHERE isHome = 1")
    fun deleteHomeLocation()
}