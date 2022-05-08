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
import com.iti.java.weatheroo.utils.Utils

class DailyTemperatureAdapter(var context: Context, var data: List<CurrentWeatherModel>) :
    RecyclerView.Adapter<DailyTemperatureAdapter.DailyViewHolder>() {

    //  var context: Context? = null


    class DailyViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var timeTextView: TextView = itemView.findViewById(R.id.timeTxtView)
        var statusImageView: ImageView = itemView.findViewById(R.id.imageView)
        var tempTextView: TextView = itemView.findViewById(R.id.tempHourly)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): DailyViewHolder {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.hourly_cell, recyclerView, false)
        val vh = DailyViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.timeTextView.setText(
            Utils.Utils.getDateTime(data.get(position).dt.toString())
           )
     //   Glide.with(context) .load(data.get(position).strSportThumb) .into(holder.statusImageView)
        holder.tempTextView.setText(data.get(position).temp.toString())

    }

    override fun getItemCount(): Int {
        return data.size

    }
    fun setDailyWeather(dailyWeatherResponse: List<CurrentWeatherModel>) {
        this.data = dailyWeatherResponse
        Log.i("TAG", "setdailyWeatherResponseList: " + dailyWeatherResponse.size)
        notifyDataSetChanged()
    }
}
