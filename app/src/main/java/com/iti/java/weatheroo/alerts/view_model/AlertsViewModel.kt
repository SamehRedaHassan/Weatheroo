package com.iti.java.weatheroo.alerts.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.Repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlertsViewModel (private val _irepo : Repository, val context: Context ): ViewModel(){


    fun getAlarms() : LiveData<List<MyAlert>> {
        return _irepo.getAllAlarms()
    }

    fun deleteAlarm(myAlert : MyAlert){
        viewModelScope.launch(Dispatchers.IO){
            _irepo.deleteAlarm(myAlert)
        }
    }

    fun addAlarm(myAlert : MyAlert){
        viewModelScope.launch(Dispatchers.IO){
            _irepo.insertAlarm(myAlert)
        }
    }
}
