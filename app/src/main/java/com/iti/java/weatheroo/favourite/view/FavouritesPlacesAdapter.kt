package com.iti.java.weatheroo.favourite.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.home.adpters.DailyTemperatureAdapter
import com.iti.java.weatheroo.model.CurrentWeatherModel
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.utils.Utils

class FavouritesPlacesAdapter (val context: Context, var data: List<FavouriteLocation>,val delgate : NavigationDelegate) :
    RecyclerView.Adapter<FavouritesPlacesAdapter.FavouritePlaceViewHolder>(){

    class FavouritePlaceViewHolder(var layout: View) : RecyclerView.ViewHolder(layout) {
        var placeTextView: TextView = itemView.findViewById(R.id.favouritePlaceTxtView)
        var deleteFavBtn : Button   = itemView.findViewById(R.id.deleteFavButtonBtn)

    }

    override fun onCreateViewHolder(recyclerView: ViewGroup, viewType: Int): FavouritePlaceViewHolder {
        val layoutInflater = LayoutInflater.from(recyclerView.context)
        val v: View =
            layoutInflater.inflate(R.layout.favourite_cell, recyclerView, false)
        val vh = FavouritesPlacesAdapter.FavouritePlaceViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: FavouritePlaceViewHolder, position: Int) {
        holder.placeTextView.setText(data.get(position).name)
        holder.deleteFavBtn.setOnClickListener{
            delgate.deleteFav(data.get(position))
        }
        holder.layout.setOnClickListener(View.OnClickListener {
            Utils.setCurrentLatitude(context,data.get(position).latitude.toString())
            Utils.setCurrentLongitude(context,data.get(position).longitude.toString())
            delgate.navigateToHome()
        })
    }

    override fun getItemCount(): Int {
        return  data.size
    }

}