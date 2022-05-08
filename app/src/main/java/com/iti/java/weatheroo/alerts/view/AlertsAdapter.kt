package com.iti.java.weatheroo.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.model.AlertModel

class AlertsAdapter (val context: Context, val data: List<AlertModel>) :
    RecyclerView.Adapter<AlertsAdapter.AlertViewHolder>(){

    class AlertViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
//        var timeTextView: TextView = itemView.findViewById(R.id.timeTxtView)
//        var statusImageView: ImageView = itemView.findViewById(R.id.imageView)
//        var tempTextView: TextView = itemView.findViewById(R.id.tempHourly)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): AlertViewHolder {
//        var timeTextView: TextView = itemView.findViewById(R.id.timeTxtView)
//        var statusImageView: ImageView = itemView.findViewById(R.id.imageView)
//        var tempTextView: TextView = itemView.findViewById(R.id.tempHourly)

        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.alert_cell, recyclerView, false)
        val vh = AlertsAdapter.AlertViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return  20
    }

}