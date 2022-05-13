package com.iti.java.weatheroo.map.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MapViewModel (private val repository: Repository, private var context : Context) : ViewModel() {
    fun addFavourite(location: FavouriteLocation) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavouriteLocation(location)
        }
    }

    fun deleteHome() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHomeLocation()
        }
    }
}