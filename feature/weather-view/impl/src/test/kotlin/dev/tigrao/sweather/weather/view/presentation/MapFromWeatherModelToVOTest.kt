package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.domain.GetBackgroundUseCase
import dev.tigrao.sweather.weather.view.domain.GetDayInformationUseCase
import dev.tigrao.sweather.weather.view.domain.TemperatureSymbolConverter
import dev.tigrao.sweather.weather.view.domain.model.*
import dev.tigrao.sweather.weather.view.presentation.model.ConditionModelVO
import dev.tigrao.sweather.weather.view.presentation.model.TemperatureModelVO
import dev.tigrao.sweather.weather.view.presentation.model.WeatherViewVO
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class MapFromWeatherModelToVOTest {

    private val symbolConverter = mockk<TemperatureSymbolConverter>()
    private val imageIconUrlFactory = mockk<ImageIconUrlFactory>()
    private val getDayInformationUseCase = mockk<GetDayInformationUseCase>()
    private val getBackgroundUseCase = mockk<GetBackgroundUseCase>()
    private val weatherListConditionsFactory = mockk<WeatherListConditionsFactory>()

    private val subject = MapFromWeatherModelToVO(
        symbolConverter,
        imageIconUrlFactory,
        getDayInformationUseCase,
        getBackgroundUseCase,
        weatherListConditionsFactory
    )

    @Test
    fun mapFrom() {
        val background = 13
        val date = "Sunday, 19 May 2019  |  4:30PM"
        val symbol = "C"
        val icon = "http://google.com.br/icon/01d.png"

        prepareScenario(
            symbol = symbol,
            icon = icon,
            dayInformation = DayInformationModel(
                date = date,
                isDay = true
            ),
            background = background,
        )
        val condition = "clear sky"

        val result = subject.mapFrom(
            WeatherLocationModel(
                location = LocationModel(
                    city = "Shuzenji",
                    country = "Japão",
                ),
                temperature = TemperatureModel(
                    temperature = "281",
                    min = "280",
                    max = "283",
                    unitType = UnitType.METRIC,
                ),
                weather = WeatherInfoModel(
                    conditionIcon = "01d",
                    condition = condition,
                    humidity = "93%",
                    pressure = "1016hPa",
                    windSpeed = "11km/h",
                    sunrise = "02:44 PM",
                    sunset = "02:45 PM",
                )
            )
        )

        val expected = WeatherViewVO(
            background = background,
            date = date,
            city = "Shuzenji",
            currentTemp = TemperatureModelVO(
                value = "281",
                symbol = symbol,
                complete = "281C"
            ),
            minTemp = TemperatureModelVO(
                value = "280",
                symbol = symbol,
                complete = "280C"
            ),
            maxTemp = TemperatureModelVO(
                value = "283",
                symbol = symbol,
                complete = "283C"
            ),
            condition = ConditionModelVO(
                icon = imageIconUrlFactory.create(
                    icon = icon,
                ),
                name = condition,
            ),
            weatherItemList = emptyList()
        )

        assertEquals(expected, result)
    }

    private fun prepareScenario(
        symbol: String,
        icon: String,
        dayInformation: DayInformationModel,
        background: Int
    ) {
        every { symbolConverter.mapFrom(any()) } returns symbol
        every { imageIconUrlFactory.create(any()) } returns icon
        every { getDayInformationUseCase() } returns dayInformation
        every { getBackgroundUseCase(any()) } returns background
        every { weatherListConditionsFactory.create(any()) } returns emptyList()
    }
}
