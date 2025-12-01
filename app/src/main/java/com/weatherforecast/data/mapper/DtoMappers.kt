package com.weatherforecast.data.mapper

import com.weatherforecast.data.remote.WeatherDto
import com.weatherforecast.domain.model.WeatherInfo

fun WeatherDto.toDomain(): WeatherInfo {
    val first = this.weather?.firstOrNull()
    return WeatherInfo(
        city = this.name ?: "Unknown",
        description = first?.description ?: "N/A",
        icon = first?.icon,
        temperature = this.main?.temp ?: 0.0,
        feelsLike = this.main?.feelsLike,
        humidity = this.main?.humidity,
        windSpeed = this.wind?.speed
    )
}
