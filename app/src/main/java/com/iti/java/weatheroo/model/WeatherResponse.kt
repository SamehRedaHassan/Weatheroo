package com.iti.java.weatheroo.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class WeatherResponse(
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: CurrentWeatherModel,
    val hourly: List<CurrentWeatherModel>,//48
    val daily: List<DailyWeatherModel>,//8
    var alerts: List<Alert>? = listOf<Alert>(),
    @PrimaryKey
    @NonNull
    var id: String = "id"
) : Serializable
