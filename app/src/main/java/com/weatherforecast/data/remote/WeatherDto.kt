import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    val coord: CoordDto?,
    val weather: List<WeatherDescriptionDto>?,
    val main: MainDto?,
    val wind: WindDto?,
    val name: String?
)

@JsonClass(generateAdapter = true)
data class CoordDto(val lon: Double?, val lat: Double?)
@JsonClass(generateAdapter = true)
data class WeatherDescriptionDto(
    val id: Int?,
    val main: String?,
    val description: String?,
    @Json(name = "icon") val icon: String?
)
@JsonClass(generateAdapter = true)
data class MainDto(
    val temp: Double?,
    val feels_like: Double?,
    val temp_min: Double?,
    val temp_max: Double?,
    val humidity: Int?
)
@JsonClass(generateAdapter = true)
data class WindDto(val speed: Double?, val deg: Int?)
