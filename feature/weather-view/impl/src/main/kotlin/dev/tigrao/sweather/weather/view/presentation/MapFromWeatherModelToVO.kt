package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.domain.model.WeatherLocationModel
import dev.tigrao.sweather.weather.view.presentation.model.ConditionModelVO
import dev.tigrao.sweather.weather.view.presentation.model.TemperatureModelVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewVO
import java.net.URL

internal class MapFromWeatherModelToVO(
    private val symbolConverter: TemperatureSymbolConverter,
) {

    fun mapFrom(from: WeatherLocationModel): WeatherViewVO {
        val symbol = symbolConverter.mapFrom(from.temperature.unitType)

        return WeatherViewVO(
            background = R.drawable.bg_weather_view_day,
            date = "Sunday, 19 May 2019  |  4:30PM",
            city = "Muambai, India",
            currentTemp = convertTemperature(symbol, from.temperature.temperature),
            minTemp = convertTemperature(symbol, from.temperature.min),
            maxTemp = convertTemperature(symbol, from.temperature.max),
            condition = ConditionModelVO(
                icon = URL("http://openweathermap.org/img/wn/01d@2x.png"),
                name = from.weather.condition,
            )
        )
    }

    private fun convertTemperature(unit: String, temp: String) = TemperatureModelVO(
        value = temp,
        symbol = unit,
        complete = "$temp$unit"
    )
}
