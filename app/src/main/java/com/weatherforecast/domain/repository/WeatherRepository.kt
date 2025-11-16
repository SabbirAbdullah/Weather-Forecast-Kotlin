interface WeatherRepository {
    suspend fun getWeatherByCity(city: String): WeatherInfo
}
