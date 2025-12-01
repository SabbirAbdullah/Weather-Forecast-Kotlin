package com.weatherforecast.presentation.weather

import com.weatherforecast.domain.model.WeatherInfo

data class WeatherState(
    val isLoading: Boolean = false,
    val weather: WeatherInfo? = null,
    val error: String? = null,
    val query: String = ""
)
