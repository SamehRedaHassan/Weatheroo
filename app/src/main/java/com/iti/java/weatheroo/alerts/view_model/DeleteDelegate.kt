package com.iti.java.weatheroo.alerts.view_model

import com.iti.java.weatheroo.model.MyAlert

interface DeleteDelegate {
   fun deleteAlarm(myAlert : MyAlert)
}