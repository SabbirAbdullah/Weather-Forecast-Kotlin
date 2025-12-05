package com.weatherforecast.domain.usecase

import com.weatherforecast.domain.model.WeatherInfo
import com.weatherforecast.domain.repository.WeatherRepository


import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(
    private val repository: WeatherRepository
) {

    // Get weather by city name
    suspend operator fun invoke(city: String): WeatherInfo {
        return repository.getWeatherByCity(city)
    }

    // New: Get weather by latitude and longitude
    suspend fun byCoordinates(lat: Double, lon: Double): WeatherInfo {
        return repository.getWeatherByCoordinates(lat, lon)
    }
}
