data class WeatherInfo(
    val city: String,
    val description: String,
    val icon: String?, // icon code for OpenWeather
    val temperature: Double,
    val feelsLike: Double?,
    val humidity: Int?,
    val windSpeed: Double?
)
