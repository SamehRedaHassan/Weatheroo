package com.iti.java.weatheroo.map.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.iti.java.weatheroo.MainActivity
import com.iti.java.weatheroo.R
import com.iti.java.weatheroo.databinding.ActivityMapsBinding
import com.iti.java.weatheroo.home.view_model.HomeViewModel
import com.iti.java.weatheroo.home.view_model.HomeViewModelFactory
import com.iti.java.weatheroo.map.view_model.MapViewModel
import com.iti.java.weatheroo.map.view_model.MapViewModelFactory
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var currentLocation : LatLng
    private lateinit var saveBtn : Button
    private lateinit var viewModel : MapViewModel
    private val retrofitService = RetrofitService.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureUI()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureUI(){
        viewModel = ViewModelProvider(
            this,
            MapViewModelFactory(RepositoryImpl(this, retrofitService,LocalSourceImpl(this)),this)
        ).get(MapViewModel::class.java)
        saveBtn = binding.saveLocationBtn
        saveBtn.visibility = View.INVISIBLE

        //favourites  save to room (Lat Lon name) //finish activity
        saveBtn.setOnClickListener {
           // saveLatLonToSharedPreferences()

            finish()
            val screen = intent.getStringExtra(Constants.SOURCE_FRAGMENT)
            when (screen){
                Constants.FAVOURITES -> {
                    val favouriteObject = FavouriteLocation(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        Utils.getCurrentCityFromLatLon( this,currentLocation.latitude , currentLocation.longitude ),false
                    )
                    viewModel.addFavourite(favouriteObject)
                    finish()
                }
                Constants.SPLASH -> {
                    saveLatLonToSharedPreferences()
                    val intent = Intent(this , MainActivity::class.java)
                    startActivity(intent)
                }

                Constants.SETTINGS -> {
                    saveLatLonToSharedPreferences()
                    finish()
                }
            }

        }
    }
private fun saveLatLonToSharedPreferences(){

        val favouriteObject = FavouriteLocation(
            currentLocation.latitude,
            currentLocation.longitude,
            Utils.getCurrentCityFromLatLon( this,currentLocation.latitude , currentLocation.longitude ),true
        )
        viewModel.deleteHome()
        viewModel.addFavourite(favouriteObject)
    Utils.setCurrentLatitude(this,currentLocation.latitude.toString())
    Utils.setCurrentLongitude(this,currentLocation.longitude.toString())

}

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        addPinLocationOnMap()
        mMap.setOnMapClickListener {
            mMap.clear()
            currentLocation = it
            val addressInText = Utils.getCurrentCityFromLatLon(this,it.latitude, it.longitude)
            mMap.addMarker(MarkerOptions().position(it).title(addressInText))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            saveBtn.visibility = View.VISIBLE
        }
    }


    private fun addPinLocationOnMap(){
        val currentLoc = LatLng(26.8206 , 30.8025)
        val addressInText = Utils.getCurrentCityFromLatLon(this,currentLoc.latitude, currentLoc.longitude)
        mMap.addMarker(MarkerOptions().position(currentLoc).title(addressInText))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLoc))
    }

}
