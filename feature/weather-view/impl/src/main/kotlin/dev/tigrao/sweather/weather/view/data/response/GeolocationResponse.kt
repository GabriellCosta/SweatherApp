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
    val temp: Float,
    @Json(name = "feels_like")
    val feelsLike: Float,
    @Json(name = "temp_min")
    val tempMin: Float,
    @Json(name = "temp_max")
    val tempMax: Float,
    val pressure: Float,
    val humidity: Int,
)

@JsonClass(generateAdapter = true)
internal class WindResponse(
    val speed: Float,
    val deg: Float,
)
