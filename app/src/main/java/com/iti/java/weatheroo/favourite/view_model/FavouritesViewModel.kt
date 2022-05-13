package com.iti.java.weatheroo.favourite.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.Repository.Repository
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavouritesViewModel(private val _irepo : Repository, val context: Context): ViewModel()  {

        fun getFavouriteLocationsFromDatabase() : LiveData<List<FavouriteLocation>> {
            return _irepo.getAllFavouriteLocations()
        }

        fun deleteFavourite(fav: FavouriteLocation){
            viewModelScope.launch(Dispatchers.IO) {
                _irepo.deleteFavouriteLocation(fav)
            }
        }

}