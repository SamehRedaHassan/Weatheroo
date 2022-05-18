package com.iti.java.weatheroo

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.airbnb.lottie.LottieAnimationView
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iti.java.weatheroo.map.view.MapsActivity
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils
import com.google.android.gms.location.*
import com.iti.java.weatheroo.utils.WeatherLocationProvider


class SplashActivity : AppCompatActivity() {

    lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude : Double? = null
    var longitude : Double? = null
   lateinit var provider :  WeatherLocationProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        provider = WeatherLocationProvider(this)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.animate().translationY(1500F).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.loop(true);
        Handler().postDelayed(Runnable {
            if(Utils.getIsFirstLaunch(this)){
                val locationOptions = arrayOf("Map", "GPS")
                var checkedItem = 1

                MaterialAlertDialogBuilder(this)
                    .setTitle(resources.getString(R.string.requestPosition))
                    .setPositiveButton(resources.getString(R.string.approvePositionRequest)) { dialog, which ->
                     if(checkedItem == 1){
                         android.util.Log.i("GGGGGGGG", "onCreate: ")
                            provider.getLocation()


                     }else if (checkedItem == 0){
                         val intent = Intent(this , MapsActivity::class.java)
                         intent.putExtra(Constants.SOURCE_FRAGMENT , Constants.SPLASH)
                         Utils.setIsFirstLaunch(this,false)
                         startActivity(intent)
                     }else{
                         Log.e("ttttttttt", "onCreate: " + which, )

                     }
                    }
                    // Single-choice items (initialized with checked item)
                    .setSingleChoiceItems(locationOptions, checkedItem) { dialog, which ->
                        checkedItem = which
                    }
                    .show()

                }else{
                startActivity(
                    Intent(
                        this,
                        MainActivity::class.java
                    )
                )
                }

        }, 3000)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 506070){
            Log.i("sandra", "onRequestPermissionsResult: $requestCode")
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getFreshLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getFreshLocation(){
        if(checkLocationPermission()){
            if(isLocationEnabled()){
                requestNewLocation()
            }else{
                enableLocationSetting()
            }
        }else{
            requestPermissionFromUser()
        }
    }

    private fun checkLocationPermission() : Boolean{
        return (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION).equals(
            PackageManager.PERMISSION_GRANTED)
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION).equals(
            PackageManager.PERMISSION_GRANTED)
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET).equals(
            PackageManager.PERMISSION_GRANTED)
                )
    }

    private fun isLocationEnabled() : Boolean{
        var locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocation(){
        var locationRequest : LocationRequest = LocationRequest.create()
        locationRequest.interval = 10
        locationRequest.fastestInterval = 5
        locationRequest.numUpdates = 1

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, Looper.myLooper()!!)
    }

    private fun enableLocationSetting() {
        val settingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(settingIntent)
    }


    private var locationCallBack : LocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            var location : Location = p0.lastLocation
            latitude = location.latitude
            longitude = location.longitude
          Utils.setCurrentLatitude(applicationContext,latitude.toString())
            Utils.setCurrentLongitude(applicationContext,longitude.toString())
            Utils.setIsFirstLaunch(applicationContext, true)
            navigateHome()
            Log.i("Sameh", "call back: $latitude and long : $longitude")

        }
    }

    private fun requestPermissionFromUser() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getFreshLocation()
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)
        }
    }
    private fun navigateHome(){
        val homeActivity = Intent(this,MainActivity::class.java)
        startActivity(homeActivity)
    }
}