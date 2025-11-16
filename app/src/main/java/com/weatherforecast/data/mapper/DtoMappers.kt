fun WeatherDto.toDomain(): WeatherInfo {
    val desc = this.weather?.firstOrNull()?.description ?: "N/A"
    val icon = this.weather?.firstOrNull()?.icon
    return WeatherInfo(
        city = this.name ?: "Unknown",
        description = desc,
        icon = icon,
        temperature = this.main?.temp ?: 0.0,
        feelsLike = this.main?.feels_like,
        humidity = this.main?.humidity,
        windSpeed = this.wind?.speed
    )
}
