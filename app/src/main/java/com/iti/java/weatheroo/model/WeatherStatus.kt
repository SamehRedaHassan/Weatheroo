package com.iti.java.weatheroo.model

import java.io.Serializable

data class WeatherStatus (
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
): Serializable