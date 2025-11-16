class WeatherRepositoryImpl(
    private val api: WeatherApi
) : WeatherRepository {
    override suspend fun getWeatherByCity(city: String): WeatherInfo {
        val dto = api.getWeatherByCity(city = city)
        return dto.toDomain()
    }
}
