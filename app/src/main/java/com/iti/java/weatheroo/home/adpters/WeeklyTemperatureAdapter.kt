package com.iti.java.weatheroo.home.adpters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.model.CurrentWeatherModel
import com.iti.java.weatheroo.model.DailyWeatherModel

class WeeklyTemperatureAdapter (val context: Context, var data: List<DailyWeatherModel>) :
    RecyclerView.Adapter<WeeklyTemperatureAdapter.WeeklyViewHolder>(){

    class WeeklyViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var dayTextView: TextView = itemView.findViewById(R.id.dayTextView)
        var statusImageView: ImageView = itemView.findViewById(R.id.statusDailyImageView)
        var tempMinTextView: TextView = itemView.findViewById(R.id.minMaxTextView)
        var tempMaxTextView: TextView = itemView.findViewById(R.id.tempMaxTextView)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): WeeklyViewHolder {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.daily_cell, recyclerView, false)
        val vh = WeeklyViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: WeeklyViewHolder, position: Int) {
           holder.dayTextView.setText(data.get(position).dt.toString())
          //  holder.tempTextView.setText(data.get(position).feelsLike.toString())
        holder.tempMaxTextView.setText(data.get(position).temp.max.toString())
        holder.tempMinTextView.setText(data.get(position).temp.min.toString())


    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setWeekelyWeather(weekelyWeatherResponse: List<DailyWeatherModel>) {
        this.data = weekelyWeatherResponse
        Log.i("TAG", "setWeeeeklyWeatherResponseList: " + weekelyWeatherResponse.size)
        notifyDataSetChanged()
    }
}