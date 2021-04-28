package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.domain.core.api.callApi
import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import dev.tigrao.sweather.weather.view.data.response.GeolocationResponse
import dev.tigrao.sweather.weather.view.domain.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

internal interface FetchWeatherDataByGeoLocationUseCase {
    suspend operator fun invoke(): Result<WeatherLocationModel, WeatherLocationErrorModel>
}

private const val HOUR_PATTERN = "hh:mm a"

internal class FetchWeatherDataByGeoLocation(
    private val api: WeatherViewApi,
    private val getLocationUseCase: GetLocationUseCase,
) : FetchWeatherDataByGeoLocationUseCase {
    override suspend fun invoke(): Result<WeatherLocationModel, WeatherLocationErrorModel> {
        return when (val location = getLocationUseCase()) {
            is Result.Error -> Result.Error(WeatherLocationErrorModel.FailToGetLocation)
            is Result.Success -> fetchData(location.data)
        }
    }

    private suspend fun fetchData(
        data: LocationProviderModel
    ): Result<WeatherLocationModel, WeatherLocationErrorModel> =
        callApi {
            api.getDataByGeographicCoordinates(
                lat = data.lat,
                lon = data.lon,
            )
        }.map(
            success = {
                val result = convertLocation(it)

                if (result != null)
                    Result.Success(result)
                else
                    Result.Error(WeatherLocationErrorModel.ApiError)
            },
            error = {
                Result.Error(WeatherLocationErrorModel.GenericError)
            }
        )

    private fun convertLocation(it: GeolocationResponse): WeatherLocationModel? {

        val locale = Locale("", it.sys.country)

        return it.weather.firstOrNull()?.let { weather ->
            WeatherLocationModel(
                location = LocationModel(
                    city = it.name,
                    country = locale.displayCountry,
                ),
                temperature = TemperatureModel(
                    temperature = it.main.temp.toInt().toString(),
                    min = it.main.tempMin.toInt().toString(),
                    max = it.main.tempMax.toInt().toString(),
                    unitType = UnitType.METRIC,
                ),
                // TODO: Move to string values
                weather = WeatherInfoModel(
                    condition = weather.main,
                    conditionIcon = weather.icon,
                    humidity = "${it.main.humidity}%",
                    pressure = "${it.main.pressure}hPa",
                    windSpeed = "${convertWindSpeed(it.wind.speed)}km/h",
                    sunrise = dateConverter(it.sys.sunrise),
                    sunset = dateConverter(it.sys.sunset),
                )
            )
        }
    }

    private fun convertWindSpeed(speed: Float): BigDecimal {
        return BigDecimal.valueOf(speed.toDouble())
            .multiply(BigDecimal(60))
            .multiply(BigDecimal(60))
            .divide(BigDecimal(1000))
            .setScale(0, RoundingMode.FLOOR)
    }

    private fun dateConverter(milli: Long): String {
        val default = Locale.getDefault()
        val dateFormat = SimpleDateFormat(HOUR_PATTERN, default)

        return Calendar.getInstance(default).run {
            timeInMillis = milli * 1000
            dateFormat.format(this.time)
        }
    }
}
