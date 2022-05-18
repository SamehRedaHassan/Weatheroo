package com.iti.java.weatheroo.model.Repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.*
import com.iti.java.weatheroo.model.MyAlert
import com.iti.java.weatheroo.model.FavouriteLocation
import com.iti.java.weatheroo.model.WeatherResponse
import com.iti.java.weatheroo.model.network.RetrofitService
import com.iti.java.weatheroo.model.room.LocalSource
import com.iti.java.weatheroo.utils.Constants
import com.iti.java.weatheroo.utils.Utils.Utils.truncateToDate
import com.iti.java.weatheroo.utils.Utils.Utils.truncateToHours
import com.iti.java.weatheroo.utils.worker.WeatherCoroutineWorker
import retrofit2.Call
import java.util.*
import java.util.concurrent.TimeUnit

private const val APP_ID = "c22e271e9ebc0d0e0e406902c6b750ee"

class RepositoryImpl(private val context: Context,
                     private val retrofitService: RetrofitService,
                     var localSource: LocalSource
) : Repository {

    private var repo: RepositoryImpl? = null

    fun getInstance(context: Context) : RepositoryImpl? {
        if (repo == null) {
            repo = RepositoryImpl(context,retrofitService,localSource)
        }
        return repo
    }

    override fun getWeatherForeCast(lat: Double, Lon: Double): Call<WeatherResponse> {
        return retrofitService.getWeather(lat,Lon,APP_ID,"metric","en")
    }

    override fun getAllFavouriteLocations(): LiveData<List<FavouriteLocation>> {
        return localSource.getAllFavouriteLocations()
    }

    override fun addFavouriteLocation(location: FavouriteLocation) {
        localSource.addFavouriteLocation(location)
    }

    override fun deleteFavouriteLocation(location: FavouriteLocation) {
        localSource.deleteFavouriteLocation(location)
    }

    override fun getFavWeatherObj(id: UUID): FavouriteLocation? {
      return  localSource.getFavWeatherObj(id)
    }

    override fun deleteHomeLocation() {
        localSource.deleteHomeLocation()
    }

    override fun getAllAlarms(): LiveData<List<MyAlert>> {
       return localSource.getAllAlarms()
    }

    override fun deleteAlarm(myAlert: MyAlert?) {
        WorkManager.getInstance(context).cancelUniqueWork(myAlert?.id.toString())
        localSource.deleteAlarm(myAlert)
    }

    override fun insertAlarm(myAlert: MyAlert?) {
        val nowCalender = Calendar.getInstance().apply { timeInMillis = myAlert?.alertTime!! }
        val nextCalender = Calendar.getInstance()
        nextCalender[Calendar.MINUTE] = nowCalender[Calendar.MINUTE]
        nextCalender[Calendar.HOUR_OF_DAY] = nowCalender[Calendar.HOUR_OF_DAY]
        val delay = nextCalender.timeInMillis - Date(System.currentTimeMillis()).time
            val newRequest = OneTimeWorkRequestBuilder<WeatherCoroutineWorker>()
                .setInputData(
                    Data.Builder().putString(Constants.UUID_KEY, myAlert?.id.toString()).build()
                )
                .setInitialDelay(delay, TimeUnit.MILLISECONDS).build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(myAlert?.id.toString(), ExistingWorkPolicy.REPLACE, newRequest)

        localSource.insertAlarm(myAlert)

    }

    override fun getAlarm(id: UUID?): MyAlert? {
        return localSource.getAlarm(id)
    }

}