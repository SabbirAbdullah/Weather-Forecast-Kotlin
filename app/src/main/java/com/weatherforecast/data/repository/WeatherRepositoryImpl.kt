package com.weatherforecast.data.repository

import com.weatherforecast.data.mapper.toDomain
import com.weatherforecast.data.remote.WeatherApi
import com.weatherforecast.domain.model.WeatherInfo
import com.weatherforecast.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherApi
) : WeatherRepository {

    override suspend fun getWeatherByCity(city: String): WeatherInfo {
        val dto = api.getWeatherByCity(city)
        return dto.toDomain()
    }

    // Add this for coordinates
    override suspend fun getWeatherByCoordinates(lat: Double, lon: Double): WeatherInfo {
        val dto = api.getWeatherByCoordinates(lat, lon)
        return dto.toDomain()
    }
}
