package com.iti.java.weatheroo.alerts.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.java.weatheroo.model.Alert
import com.iti.java.weatheroo.model.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertsViewModel (private val _irepo : Repository, val context: Context): ViewModel(){


    fun getAlarms() : LiveData<List<Alert>> {
        return _irepo.getAllAlarms()
    }

    fun deleteAlarm(alert : Alert){
        viewModelScope.launch(Dispatchers.IO){
            _irepo.deleteAlarm(alert)
        }
    }

    fun addAlarm(alert : Alert){
        viewModelScope.launch(Dispatchers.IO){
            _irepo.insertAlarm(alert)
        }
    }
}
