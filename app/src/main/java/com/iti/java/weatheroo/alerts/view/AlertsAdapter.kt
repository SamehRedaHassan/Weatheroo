package com.iti.java.weatheroo.alerts.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.alerts.view_model.DeleteDelegate
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.utils.Utils

class AlertsAdapter (val context: Context, var data: List<MyAlert> , val alertDeleteDelegate: DeleteDelegate) :
    RecyclerView.Adapter<AlertsAdapter.AlertViewHolder>(){

    class AlertViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var startTimeTxtView: TextView = itemView.findViewById(R.id.startTimeTxtView)
        var endTimeTxtView: TextView = itemView.findViewById(R.id.endTimeTxtView)
        var deleteBtn : Button   = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): AlertViewHolder {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.alert_cell, recyclerView, false)
        val vh = AlertsAdapter.AlertViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) {
        holder.startTimeTxtView. setText(  Utils.longToDateAsString(data.get(position).startDate , Utils.getCurrentLang(context)))
        holder.endTimeTxtView.setText(  Utils.longToDateAsString(data.get(position).endDate , Utils.getCurrentLang(context)))
        holder.deleteBtn.setOnClickListener{
            alertDeleteDelegate.deleteAlarm(data.get(position))
        }
    }

    override fun getItemCount(): Int {
        return  data.size
    }

}