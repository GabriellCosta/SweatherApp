package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.model.WeatherInfoModel
import dev.tigrao.sweather.weather.view.presentation.model.WeatherItemVO

internal class WeatherListConditionsFactory {

    fun create(from: WeatherInfoModel) =
        listOf(
            WeatherItemVO(
                icon = R.drawable.ic_humidity,
                value = from.humidity,
                name = R.string.weather_condition_humidity
            ),
            WeatherItemVO(
                icon = R.drawable.ic_pressure,
                value = from.pressure,
                name = R.string.weather_condition_pressure
            ),
            WeatherItemVO(
                icon = R.drawable.ic_wind,
                value = from.windSpeed,
                name = R.string.weather_condition_wind
            ),
            WeatherItemVO(
                icon = R.drawable.ic_sunrise,
                value = from.sunrise,
                name = R.string.weather_condition_sunrise
            ),
            WeatherItemVO(
                icon = R.drawable.ic_sunset,
                value = from.sunset,
                name = R.string.weather_condition_sunset
            ),
        )
}
