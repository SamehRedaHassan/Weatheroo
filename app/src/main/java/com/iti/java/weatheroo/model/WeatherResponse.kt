package com.iti.java.weatheroo.model

import java.io.Serializable

data class WeatherResponse (
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezoneOffset: Long,
    val current: CurrentWeatherModel,
    val hourly: List<CurrentWeatherModel>,//48
    val daily: List<DailyWeatherModel>,//8
    var alerts : List<Alert>? =  listOf<Alert>()
):Serializable
