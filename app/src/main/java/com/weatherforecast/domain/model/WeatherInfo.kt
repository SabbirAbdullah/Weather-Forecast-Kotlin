package com.weatherforecast.domain.model

data class WeatherInfo(
    val city: String,
    val description: String,
    val icon: String?,
    val temperature: Double,
    val feelsLike: Double?,
    val humidity: Int?,
    val windSpeed: Double?
)
