package com.iti.java.weatheroo.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.model.Alert

class AlertsAdapter (val context: Context, var data: List<Alert>) :
    RecyclerView.Adapter<AlertsAdapter.AlertViewHolder>(){

    class AlertViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var startTimeTxtView: TextView = itemView.findViewById(R.id.startTimeTxtView)
        var startDateTxtView: TextView = itemView.findViewById(R.id.startDateTxtView)
        var endTimeTxtView: TextView = itemView.findViewById(R.id.endTimeTxtView)
        var endDateTxtView: TextView = itemView.findViewById(R.id.endDateTxtView)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): AlertViewHolder {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.alert_cell, recyclerView, false)
        val vh = AlertsAdapter.AlertViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.startTimeTxtView.setText(data.get(position).startDate.toString())
    //    holder.startDateTxtView.setText(data.get(position).alertTime.toString())
        holder.endTimeTxtView.setText(data.get(position).endDate.toString())
    //    holder.endDateTxtView.setText(data.get(position).alertTime.toString())
    }

    override fun getItemCount(): Int {
        return  data.size
    }

}