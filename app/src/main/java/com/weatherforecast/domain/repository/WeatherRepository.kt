package com.weatherforecast.domain.repository

import com.weatherforecast.domain.model.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): WeatherInfo
}
