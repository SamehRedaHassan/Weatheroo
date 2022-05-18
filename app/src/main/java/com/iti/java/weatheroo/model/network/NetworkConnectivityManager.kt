package com.iti.java.weatheroo.model.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkConnectivityManager : BroadcastReceiver() {

    companion object{
        var isConnected = false
    }

    override fun onReceive(context: Context?, intent: Intent) {
        val status = NetworkUtility.getConnectivityStatusString(context!!)
        if ("android.net.conn.CONNECTIVITY_CHANGE" == intent.action) {
            isConnected = status != NetworkUtility.NETWORK_STATUS_NOT_CONNECTED
        }
    }
}