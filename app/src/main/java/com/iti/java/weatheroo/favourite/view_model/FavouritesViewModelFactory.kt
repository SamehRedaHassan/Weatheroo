package com.iti.java.weatheroo.favourite.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.Repository.Repository

class FavouritesViewModelFactory(private val _irepo : Repository, val context : Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouritesViewModel::class.java)){
            FavouritesViewModel(_irepo,context) as T
        }else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}