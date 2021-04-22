package dev.tigrao.sweather.weather.view.data.response

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class GeolocationResponse(
    val id: Long,
    val weather: List<WeatherResponse>,
    val main: MainResponse,
    val wind: WindResponse,
    val name: String,
    val sys: SysResponse,
)

@JsonClass(generateAdapter = true)
internal class WeatherResponse(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)

@JsonClass(generateAdapter = true)
internal class MainResponse(
    val temp: Int,
    @Json(name = "feels_like")
    val feelsLike: Int,
    @Json(name = "temp_min")
    val tempMin: Int,
    @Json(name = "temp_max")
    val tempMax: Int,
    val pressure: Int,
    val humidity: Int,
)

@JsonClass(generateAdapter = true)
internal class WindResponse(
    val speed: Float,
    val deg: Float,
)

@JsonClass(generateAdapter = true)
internal data class SysResponse(
    val sunrise: Long,
    val sunset: Long,
)
