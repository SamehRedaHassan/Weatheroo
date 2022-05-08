package com.iti.java.weatheroo.model

import java.io.Serializable

data class DailyWeatherModel (
    val dt: Long,
  //  val sunrise: Long,
  //  val sunset: Long,
 //   val moonrise: Long,
 //   val moonset: Long,
 //   val moonPhase: Double,
    val temp: TempModel,
 //   val feelsLike: FeelsLikeModel,
    val pressure: Long,
    val humidity: Long,
  //  val dewPoint: Double,
    val windSpeed: Double,
  //  val windDeg: Long,
 //   val windGust: Double,
    val weather: List<WeatherStatus>
    //,
 //   val clouds: Long,
  //  val pop: Long
   // , val uvi: Double
): Serializable
