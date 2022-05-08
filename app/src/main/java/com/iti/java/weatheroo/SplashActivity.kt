package com.iti.java.weatheroo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.airbnb.lottie.LottieAnimationView
import android.content.Intent
import android.os.Handler


class SplashActivity : AppCompatActivity() {

    lateinit var lottieAnimationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        lottieAnimationView = findViewById(R.id.lottie);
        lottieAnimationView.animate().translationY(1500F).setDuration(1000).setStartDelay(5000);
        lottieAnimationView.loop(true);
        Handler().postDelayed(Runnable {
            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }, 3000)

    }
}