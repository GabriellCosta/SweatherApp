package dev.tigrao.sweather.weather.view.presentation.model

import androidx.annotation.DrawableRes
import java.net.URL

internal data class WeatherViewVO(
    @DrawableRes val background: Int,
    val date: String,
    val city: String,
    val condition: ConditionModelVO,
    val currentTemp: TemperatureModelVO,
    val minTemp: TemperatureModelVO,
    val maxTemp: TemperatureModelVO,
)

internal data class ConditionModelVO(
    val icon: String,
    val name: String,
)

internal class TemperatureModelVO(
    val value: String,
    val symbol: String,
    val complete: String,
)
