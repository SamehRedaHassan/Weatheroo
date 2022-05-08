package com.iti.java.weatheroo.favourite.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.home.adpters.DailyTemperatureAdapter
import com.iti.java.weatheroo.model.CurrentWeatherModel

class FavouritesPlacesAdapter (val context: Context, val data: List<CurrentWeatherModel>) :
    RecyclerView.Adapter<FavouritesPlacesAdapter.FavouritePlaceViewHolder>(){

    class FavouritePlaceViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
//        var timeTextView: TextView = itemView.findViewById(R.id.timeTxtView)
//        var statusImageView: ImageView = itemView.findViewById(R.id.imageView)
//        var tempTextView: TextView = itemView.findViewById(R.id.tempHourly)
    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): FavouritePlaceViewHolder {
//        var timeTextView: TextView = itemView.findViewById(R.id.timeTxtView)
//        var statusImageView: ImageView = itemView.findViewById(R.id.imageView)
//        var tempTextView: TextView = itemView.findViewById(R.id.tempHourly)

        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.favourite_cell, recyclerView, false)
        val vh = FavouritesPlacesAdapter.FavouritePlaceViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: FavouritePlaceViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return  20
    }

}