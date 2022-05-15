package com.iti.java.weatheroo.alerts.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.Repository.Repository

class AlertsViewModelFactory(private val _irepo : Repository, val context : Context): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlertsViewModel::class.java)){
            AlertsViewModel(_irepo,context) as T
        }else{
            throw IllegalArgumentException("HomeViewModel class not found")
        }
    }
}