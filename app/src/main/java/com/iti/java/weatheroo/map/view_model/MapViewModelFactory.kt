package com.iti.java.weatheroo.map.view_model

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iti.java.weatheroo.model.Repository.Repository

class MapViewModelFactory constructor(private val repository: Repository, private var context : Context): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            MapViewModel(this.repository , this.context) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}