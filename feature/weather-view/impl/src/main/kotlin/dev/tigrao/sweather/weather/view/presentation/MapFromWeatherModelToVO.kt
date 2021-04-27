package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.domain.GetBackgroundUseCase
import dev.tigrao.sweather.weather.view.domain.GetDayInformationUseCase
import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.domain.model.WeatherLocationModel
import dev.tigrao.sweather.weather.view.presentation.model.ConditionModelVO
import dev.tigrao.sweather.weather.view.presentation.model.TemperatureModelVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewVO

internal class MapFromWeatherModelToVO(
    private val symbolConverter: TemperatureSymbolConverter,
    private val imageIconUrlFactory: ImageIconUrlFactory,
    private val getDayInformationUseCase: GetDayInformationUseCase,
    private val getBackgroundUseCase: GetBackgroundUseCase,
    private val weatherListConditionsFactory: WeatherListConditionsFactory,
) {

    fun mapFrom(from: WeatherLocationModel): WeatherViewVO {
        val symbol = symbolConverter.mapFrom(from.temperature.unitType)
        val dayInformation = getDayInformationUseCase()

        return WeatherViewVO(
            background = getBackgroundUseCase(dayInformation.isDay),
            date = dayInformation.date,
            city = from.location.city,
            currentTemp = convertTemperature(symbol, from.temperature.temperature),
            minTemp = convertTemperature(symbol, from.temperature.min),
            maxTemp = convertTemperature(symbol, from.temperature.max),
            condition = ConditionModelVO(
                icon = imageIconUrlFactory.create(
                    icon = from.weather.conditionIcon,
                ),
                name = from.weather.condition,
            ),
            weatherItemList = weatherListConditionsFactory.create(from.weather)
        )
    }

    private fun convertTemperature(unit: String, temp: String) = TemperatureModelVO(
        value = temp,
        symbol = unit,
        complete = "$temp$unit"
    )
}
