package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.domain.model.WeatherLocationModel
import dev.tigrao.sweather.weather.view.presentation.model.ConditionModelVO
import dev.tigrao.sweather.weather.view.presentation.model.TemperatureModelVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewVO
import java.text.SimpleDateFormat
import java.util.*

internal class MapFromWeatherModelToVO(
    private val symbolConverter: TemperatureSymbolConverter,
    private val imageIconUrlFactory: ImageIconUrlFactory,
) {

    fun mapFrom(from: WeatherLocationModel): WeatherViewVO {
        val time = getCurrentTime()
        val symbol = symbolConverter.mapFrom(from.temperature.unitType)

        return WeatherViewVO(
            background = R.drawable.bg_weather_view_day,
            date = getDatetime(time),
            city = from.location.city,
            currentTemp = convertTemperature(symbol, from.temperature.temperature),
            minTemp = convertTemperature(symbol, from.temperature.min),
            maxTemp = convertTemperature(symbol, from.temperature.max),
            condition = ConditionModelVO(
                icon = imageIconUrlFactory.create(
                    icon = from.weather.conditionIcon,
                ),
                name = from.weather.condition,
            )
        )
    }

    private fun getDatetime(calendar: Calendar): String {
        val format = SimpleDateFormat("E, dd MMM yyyy | HH:mm", Locale.getDefault())

        return format.format(calendar.time)
    }

    private fun getCurrentTime() = Calendar.getInstance()

    private fun convertTemperature(unit: String, temp: String) = TemperatureModelVO(
        value = temp,
        symbol = unit,
        complete = "$temp$unit"
    )
}
