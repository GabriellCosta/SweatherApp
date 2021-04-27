package dev.tigrao.sweather.weather.view.presentation

import dev.tigrao.sweather.weather.view.R
import dev.tigrao.sweather.weather.view.domain.model.WeatherInfoModel
import dev.tigrao.sweather.weather.view.presentation.model.WeatherItemVO
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherListConditionsFactoryTest {

    private val subject = WeatherListConditionsFactory()

    @Test
    fun create_returnCorrectList() {
        val model = WeatherInfoModel(
            conditionIcon = "01d",
            condition = "clear Sky",
            humidity = "93%",
            pressure = "1016hPa",
            windSpeed = "11km/h",
            sunrise = "02:44 PM",
            sunset = "02:45 PM",
        )

        val result = subject.create(model)

        val expected = listOf(
            WeatherItemVO(
                icon = R.drawable.ic_humidity,
                value = "93%",
                name = R.string.weather_condition_humidity
            ),
            WeatherItemVO(
                icon = R.drawable.ic_pressure,
                value = "1016hPa",
                name = R.string.weather_condition_pressure
            ),
            WeatherItemVO(
                icon = R.drawable.ic_wind,
                value = "11km/h",
                name = R.string.weather_condition_wind
            ),
            WeatherItemVO(
                icon = R.drawable.ic_sunrise,
                value = "02:44 PM",
                name = R.string.weather_condition_sunrise
            ),
            WeatherItemVO(
                icon = R.drawable.ic_sunset,
                value = "02:45 PM",
                name = R.string.weather_condition_sunset
            ),
        )

        assertEquals(expected, result)
    }
}
