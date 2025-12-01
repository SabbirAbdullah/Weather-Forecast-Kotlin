package com.weatherforecast.presentation.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherforecast.domain.usecase.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    init {
        // Optionally restore last query from saved state
        savedStateHandle.get<String>("last_query")?.let { q ->
            state = state.copy(query = q)
        }
    }

    fun onQueryChange(q: String) {
        state = state.copy(query = q)
        savedStateHandle["last_query"] = q
    }

    fun search() {
        val city = state.query.trim()
        if (city.isEmpty()) {
            state = state.copy(error = "Please enter a city")
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val info = getWeatherUseCase(city)
                state = state.copy(isLoading = false, weather = info)
            } catch (e: Exception) {
                state = state.copy(isLoading = false, error = e.localizedMessage ?: "An error occurred")
            }
        }
    }

    fun clearError() {
        state = state.copy(error = null)
    }
}
