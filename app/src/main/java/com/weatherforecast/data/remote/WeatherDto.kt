package com.weatherforecast.data.remote

import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("coord") val coord: CoordDto?,
    @SerializedName("weather") val weather: List<WeatherDescriptionDto>?,
    @SerializedName("main") val main: MainDto?,
    @SerializedName("wind") val wind: WindDto?,
    @SerializedName("name") val name: String?
)

data class CoordDto(val lon: Double?, val lat: Double?)
data class WeatherDescriptionDto(
    val id: Int?,
    val main: String?,
    val description: String?,
    @SerializedName("icon") val icon: String?
)
data class MainDto(
    val temp: Double?,
    @SerializedName("feels_like") val feelsLike: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    val humidity: Int?
)
data class WindDto(val speed: Double?, val deg: Int?)
