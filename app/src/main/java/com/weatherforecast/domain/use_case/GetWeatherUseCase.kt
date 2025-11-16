class GetWeatherUseCase(private val repository: WeatherRepository) {
    suspend operator fun invoke(city: String): WeatherInfo {
        return repository.getWeatherByCity(city)
    }
}
