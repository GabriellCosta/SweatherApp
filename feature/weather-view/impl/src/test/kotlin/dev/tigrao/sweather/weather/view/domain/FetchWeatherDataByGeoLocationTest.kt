package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import dev.tigrao.sweather.weather.view.data.response.*
import dev.tigrao.sweather.weather.view.domain.model.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class FetchWeatherDataByGeoLocationTest {

    private val api = mockk<WeatherViewApi>()
    private val getLocationUseCase = mockk<GetLocationUseCase>()

    private val subject = FetchWeatherDataByGeoLocation(api, getLocationUseCase)

    @Before
    fun setup() {
        Locale.setDefault(Locale("pt", "BR"))
    }

    @Test
    fun invoke_withSuccess_returnCorrectModel() = runBlocking {
        val response = GeolocationResponse(
            id = 43212,
            weather = listOf(
                WeatherResponse(
                    id = 1333,
                    main = "clear sky",
                    description = "very clear sky",
                    icon = "01d"
                )
            ),
            main = MainResponse(
                temp = 281.52,
                feelsLike = 278.99,
                tempMin = 280.15,
                tempMax = 283.71,
                pressure = 1016,
                humidity = 93,
            ),
            wind = WindResponse(
                speed = 3.09f,
                deg = 107.538f,
            ),
            name = "Shuzenji",
            sys = SysResponse(
                sunrise = 1619515532,
                sunset = 1619124401,
                country = "JP"
            )
        )

        prepareScenario(
            response = response,
            location = Result.Success(LocationProviderModel(13.0, 13.0))
        )

        val result = subject()

        val expected = WeatherLocationModel(
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
                condition = "clear sky",
                humidity = "93%",
                pressure = "1016hPa",
                windSpeed = "11km/h",
                sunrise = "06:25 AM",
                sunset = "05:46 PM",
            )
        )

        result as Result.Success

        assertEquals(expected, result.data)
    }

    @Test
    fun invoke_withoutWeatherList_returnApiErrorModel() = runBlocking {
        val response = GeolocationResponse(
            id = 43212,
            weather = listOf(),
            main = MainResponse(
                temp = 281.52,
                feelsLike = 278.99,
                tempMin = 280.15,
                tempMax = 283.71,
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
                country = "JP"
            )
        )

        prepareScenario(
            response = response,
            location = Result.Success(LocationProviderModel(13.0, 13.0))
        )

        val result = subject()

        result as Result.Error

        assertEquals(WeatherLocationErrorModel.ApiError, result.errorResult)
    }

    @Test
    fun invoke_apiError_returnGenericErrorModel() = runBlocking {

        prepareScenario(
            apiError = true,
            location = Result.Success(LocationProviderModel(13.0, 13.0))
        )

        val result = subject()

        result as Result.Error

        assertEquals(WeatherLocationErrorModel.GenericError, result.errorResult)
    }

    @Test
    fun invoke_locationError_returnFailToGetLocationErrorModel() = runBlocking {

        prepareScenario(
            apiError = true,
            location = Result.Error(mockk())
        )

        val result = subject()

        result as Result.Error

        assertEquals(WeatherLocationErrorModel.FailToGetLocation, result.errorResult)
    }

    @Test
    fun invoke_callApiWithLocationResponse() = runBlocking {
        val response = GeolocationResponse(
            id = 43212,
            weather = listOf(
                WeatherResponse(
                    id = 1333,
                    main = "clear sky",
                    description = "very clear sky",
                    icon = "01d"
                )
            ),
            main = MainResponse(
                temp = 281.52,
                feelsLike = 278.99,
                tempMin = 280.15,
                tempMax = 283.71,
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
                country = "JP"
            )
        )

        prepareScenario(
            response = response,
            apiError = false,
            location = Result.Success(LocationProviderModel(13.0, 13.0))
        )

        subject()

        coVerify(exactly = 1) {
            api.getDataByGeographicCoordinates(13.0, 13.0)
        }
    }

    private fun prepareScenario(
        response: GeolocationResponse = mockk(),
        location: Result<LocationProviderModel, LocationProviderErrorModel>,
        apiError: Boolean = false,
    ) {

        if (apiError)
            coEvery {
                api.getDataByGeographicCoordinates(
                    any(),
                    any()
                )
            } throws IllegalArgumentException()
        else
            coEvery { api.getDataByGeographicCoordinates(any(), any()) } returns response

        coEvery { getLocationUseCase() } returns location
    }
}
