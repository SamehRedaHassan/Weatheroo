package com.iti.java.weatheroo

import android.app.Activity
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
import android.util.Log
import android.provider.Settings
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.iti.java.weatheroo.map.view.MapsActivity
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils


class SplashActivity : AppCompatActivity() {

    lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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
                         fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                         getCurrentLoc()
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

    private fun getCurrentLoc() {
        if(checkPermission()) {
            if(isLocationEnabeled())
            {
                //find lat and long
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {task ->
                    val location : Location? = task.result
                    if(location==null) {
                        Toast.makeText(this , "Null location returned" , Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this , "location returned successfully lat is : ${location.latitude} and long is : ${location.longitude} " , Toast.LENGTH_LONG).show()
                        Utils.setCurrentLatitude(this,location.latitude.toString())
                        Utils.setCurrentLongitude(this,location.longitude.toString())
                        val intent = Intent(this , MainActivity::class.java)
                        Utils.setIsFirstLaunch(this,false)
                        startActivity(intent)
                    }
                }
            }
            else
            {
                //setting open here
                Toast.makeText(this , "Please turn on Location" , Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else
        {
            //request permission here
            requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this as Activity,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION
                ,android.Manifest.permission.ACCESS_FINE_LOCATION) , 1234)
    }

    private fun checkPermission(): Boolean {
        if(ActivityCompat.checkSelfPermission(this ,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this ,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED)
        {
            return true
        }
        return false
    }

    private fun isLocationEnabeled(): Boolean {
        val locationManager : LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
}