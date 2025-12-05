package com.weatherforecast.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
interface WeatherApi {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") city: String,
        @Query("appid") apiKey: String = com.weatherforecast.BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherDto

    @GET("weather")
    suspend fun getWeatherByCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = com.weatherforecast.BuildConfig.OPENWEATHER_API_KEY,
        @Query("units") units: String = "metric"
    ): WeatherDto
}
