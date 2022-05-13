package com.iti.java.weatheroo.home.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.Repository.Repository

class HomeViewModelFactory(private val _irepo : Repository, val context : Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
            HomeViewModel(_irepo,context) as T
        }else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}