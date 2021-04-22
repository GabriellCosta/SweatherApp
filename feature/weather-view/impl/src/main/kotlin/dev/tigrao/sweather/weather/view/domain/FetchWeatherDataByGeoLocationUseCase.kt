package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.domain.core.api.callApi
import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import dev.tigrao.sweather.weather.view.domain.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

internal interface FetchWeatherDataByGeoLocationUseCase {
    suspend operator fun invoke(): Result<WeatherLocationModel, WeatherLocationErrorModel>
}

internal class FetchWeatherDataByGeoLocation(
    private val api: WeatherViewApi,
) : FetchWeatherDataByGeoLocationUseCase {
    override suspend fun invoke(): Result<WeatherLocationModel, WeatherLocationErrorModel> {
        return callApi {
            api.getDataByGeographicCoordinates(
                lat = -23.533773,
                lon = -46.625290,
            )
        }.transformMap(
            success = {
                WeatherLocationModel(
                    location = LocationModel(
                        city = "",
                        country = "",
                        date = "",
                    ),
                    temperature = TemperatureModel(
                        temperature = it.main.temp.toString(),
                        min = it.main.tempMin.toString(),
                        max = it.main.tempMax.toString(),
                        unitType = UnitType.STANDARD,
                    ),
                    // TODO: Move to string values
                    weather = WeatherInfoModel(
                        humidity = "${it.main.humidity}%",
                        pressure = "${it.main.pressure}hPa",
                        windSpeed = "${convertWindSpeed(it.wind.speed)}km/h",
                        sunrise = dateConverter(it.sys.sunrise),
                        sunset = dateConverter(it.sys.sunset),
                    )
                )
            },
            error = {
                WeatherLocationErrorModel.GenericError
            }
        )
    }

    private fun convertWindSpeed(speed: Float): BigDecimal {
        return BigDecimal.valueOf(speed.toDouble())
            .multiply(BigDecimal(60))
            .multiply(BigDecimal(60))
            .divide(BigDecimal(1000))
            .setScale(0, RoundingMode.FLOOR)
    }

    private fun dateConverter(milli: Long): String {
        val dateFormat = SimpleDateFormat("hh:mm a")

        return Calendar.getInstance().run {
            timeInMillis = milli
            dateFormat.format(this.time)
        }
    }
}
