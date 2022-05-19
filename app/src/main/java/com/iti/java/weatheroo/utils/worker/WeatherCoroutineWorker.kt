package com.iti.java.weatheroo.utils.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.*

import java.util.*
//import java.util.concurrent.TimeUnit


import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.provider.Settings

import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.annotation.RequiresApi
import kotlinx.coroutines.launch
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.Repository.Repository
import com.iti.java.weatheroo.model.Repository.RepositoryImpl
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSourceImpl
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


class WeatherCoroutineWorker(val context: Context, val params: WorkerParameters) :
    CoroutineWorker(context, params) {
    lateinit var repo: Repository
    var customNotificationDialogView: View? = null
    var mp: MediaPlayer? = null
    var cnt = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        //suspend function work in background on default dispatcher
        //can't update ui on background thread
        //call to network here no coroutine needed
        repo = RepositoryImpl(
            context, RetrofitService.getInstance(),
            LocalSourceImpl(context)
        )
        val id = inputData.getString(Constants.UUID_KEY)
        val alert = repo.getAlarm(UUID.fromString(id))

        if (alert == null) {
            Log.e("Work", "doWork: Null alert")
        } else if (alert?.typeBool == true) {
            Log.e("Work", "doWork:  Iam true")
            val msg = getAlertsFromApi(id!!)
            if (Settings.canDrawOverlays(context)) {
                GlobalScope.launch(Dispatchers.Main) {
                    displayNotification(cnt.toString(), "Weather Notification", msg)
                    setMyWindowManger(msg, msg)
                }
            } else {
                displayNotification(cnt.toString(), msg, msg)
            }
            enableNextAlarm(alert)
        } else {
            Log.e("sameh", "doWork:  Iam false")

            enableNextAlarm(alert)
        }
        return Result.success()
    }

    private fun enableNextAlarm(myAlert: MyAlert) {
        val calender = Calendar.getInstance()
        val currentDate = Date(calender.timeInMillis)
        calender.add(Calendar.DATE, 1)
        if (calender.time.before(Date(myAlert.endDate))) {
            val nextTimeDelay = calender.timeInMillis - currentDate.time
            val nextRequest = OneTimeWorkRequestBuilder<WeatherCoroutineWorker>()
                .setInputData(
                    Data.Builder().putString(Constants.UUID_KEY, myAlert?.id.toString()).build()
                )
                .setInitialDelay(nextTimeDelay, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(myAlert?.id.toString(), ExistingWorkPolicy.REPLACE, nextRequest)

        }
    }

    private fun displayNotification(id: String, task: String, desc: String) {
        val channelId = "channel-01"
        val channelName = "Channel Name"
        val importance = NotificationManager.IMPORTANCE_HIGH
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = channelName
            val channel = NotificationChannel(channelId, name, importance)
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(com.iti.java.weatheroo.R.drawable.alarm_24)
            .setContentTitle(task)
            .setContentText(desc)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.packageName + "/" + com.iti.java.weatheroo.R.raw.bell))
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(Integer.valueOf(id), builder.build())
    }

    private fun setMyWindowManger(name: String, des: String) {

        val inflater =
            applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        customNotificationDialogView =
            inflater.inflate(com.iti.java.weatheroo.R.layout.dialoge, null)

        initView(customNotificationDialogView!!, name, des)
        if (mp == null) {
            mp = MediaPlayer.create(applicationContext, com.iti.java.weatheroo.R.raw.bell);

        }
        mp?.start();


        val LAYOUT_FLAG: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            LayoutParams.TYPE_PHONE
        }
        var windowManager =
            applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = (applicationContext.resources.displayMetrics.widthPixels * 0.85).toInt()

        val params = LayoutParams(
            width,
            LayoutParams.WRAP_CONTENT,
            LAYOUT_FLAG,
            LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )
        CoroutineScope(Dispatchers.Main).launch {
            windowManager.addView(customNotificationDialogView, params)
        }
    }

    private fun initView(dialogView: View, name: String, des: String) {

        val alarmName =
            dialogView.findViewById<TextView>(com.iti.java.weatheroo.R.id.alarmDialogNameId)
        val okBtn =
            dialogView.findViewById<Button>(com.iti.java.weatheroo.R.id.AlarmDialogOkayButtonId)
        alarmName.text = name
        okBtn.setOnClickListener {
            mp?.release()
            close()
        }
    }

    private fun close() {
        try {
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).removeView(
                customNotificationDialogView
            )
            customNotificationDialogView!!.invalidate()
            (customNotificationDialogView!!.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error", e.toString())
        }
    }

    private suspend fun getAlertsFromApi(alarmId: String): String {
        var stringMsg = "No thing to worry about "
        var event = ""
        var alarmObj: MyAlert?
        var response: Call<WeatherResponse>? = null
        alarmObj = repo.getAlarm(UUID.fromString(alarmId))
        response =
            repo.getWeatherForeCast(
                Utils.getCurrentLattitude(context),
                Utils.getCurrentLongitude(context)
            )
        response.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                if (response.body() != null) {
                    if (!(response.body()?.alerts.isNullOrEmpty())) {
                        for (item in response.body()?.alerts!!) {
                            event = item.tags[0]
                            stringMsg =
                                if (event == alarmObj?.reasonOfAlarm.toString()) { //the alert matches
                                    "unstable weather please becareful"
                                } else { //the alert doesn't match
                                    "No thing to worry about "
                                }
                        } //there is no alerts
                    }
                    //the list is empty
                    else {
                        stringMsg = "No thing to worry about "
                    }
                }


                Log.i("QQQQQQQQ", "onResponse: " + response.body())

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("FFFFFF", "onResponse: ")
            }

        })
        return stringMsg
    }
}


