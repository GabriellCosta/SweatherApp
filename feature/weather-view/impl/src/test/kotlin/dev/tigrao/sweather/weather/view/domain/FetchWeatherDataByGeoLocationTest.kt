package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import dev.tigrao.sweather.weather.view.data.response.GeolocationResponse
import dev.tigrao.sweather.weather.view.data.response.MainResponse
import dev.tigrao.sweather.weather.view.data.response.SysResponse
import dev.tigrao.sweather.weather.view.data.response.WindResponse
import dev.tigrao.sweather.weather.view.domain.model.*
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchWeatherDataByGeoLocationTest {

    private val api = mockk<WeatherViewApi>()

    private val subject = FetchWeatherDataByGeoLocation(api)

    @Test
    fun invoke_withSuccess_returnCorrectModel() = runBlocking {
        val response = GeolocationResponse(
            id = 43212,
            weather = listOf(),
            main = MainResponse(
                temp = 281.52f.toInt(),
                feelsLike = 278.99f.toInt(),
                tempMin = 280.15f.toInt(),
                tempMax = 283.71f.toInt(),
                pressure = 1016,
                humidity = 93,
            ),
            wind = WindResponse(
                speed = 3.09f,
                deg = 107.538f,
            ),
            name = "Shuzenji",
            sys = SysResponse(
                sunrise = 1619083372,
                sunset = 1619124401,
            )
        )

        prepareScenario(
            response = response,
        )

        val result = subject()

        val expected = WeatherLocationModel(
            location = LocationProviderModel(
                city = "",
                country = "",
                date = "",
            ),
            temperature = TemperatureModel(
                temperature = "281",
                min = "280",
                max = "283",
                unitType = UnitType.STANDARD,
            ),
            weather = WeatherInfoModel(
                humidity = "93%",
                pressure = "1016hPa",
                windSpeed = "11km/h",
                sunrise = "02:44 PM",
                sunset = "02:45 PM",
            )
        )

        result as Result.Success

        assertEquals(expected, result.data)
    }

    private fun prepareScenario(response: GeolocationResponse) {
        coEvery { api.getDataByGeographicCoordinates(any(), any()) } returns response
    }
}
