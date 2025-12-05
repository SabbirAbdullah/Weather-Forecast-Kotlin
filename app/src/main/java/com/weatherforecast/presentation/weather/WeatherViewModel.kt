package com.weatherforecast.presentation.weather

import androidx.compose.runtime.*
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherforecast.data.location.LocationManager
import com.weatherforecast.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val locationManager: LocationManager,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun onQueryChange(q: String) {
        state = state.copy(query = q)
    }

    fun clearError() {
        state = state.copy(error = null)
    }

    fun search(city: String = state.query) {
        if (city.isBlank()) {
            state = state.copy(error = "Please enter a city name")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            try {
                val weather = getWeatherUseCase(city)
                state = state.copy(weather = weather, isLoading = false)
            } catch (e: Exception) {
                state = state.copy(error = "City not found", isLoading = false)
            }
        }
    }

    fun getWeatherByLocation() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)

                val loc = locationManager.getCurrentLocation()
                    ?: throw Exception("Unable to detect location")

                val weather = getWeatherUseCase.byCoordinates(loc.latitude, loc.longitude)

                val city = locationManager.getCityName(loc.latitude, loc.longitude)

                state = state.copy(
                    weather = weather,
                    query = city,
                    isLoading = false
                )

                savedStateHandle["last_query"] = city

            } catch (e: Exception) {
                state = state.copy(
                    error = e.message ?: "Failed to fetch location weather",
                    isLoading = false
                )
            }
        }
    }



}
