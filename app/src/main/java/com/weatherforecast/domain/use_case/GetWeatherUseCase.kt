package com.weatherforecast.domain.usecase

import com.weatherforecast.domain.model.WeatherInfo
import com.weatherforecast.domain.repository.WeatherRepository

class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): WeatherInfo {
        return repository.getWeatherByCity(city)
    }
}
