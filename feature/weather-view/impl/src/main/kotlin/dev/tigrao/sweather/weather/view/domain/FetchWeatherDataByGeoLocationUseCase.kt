package dev.tigrao.sweather.weather.view.domain

import android.location.Location
import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.domain.core.api.callApi
import dev.tigrao.sweather.weather.view.data.WeatherViewApi
import dev.tigrao.sweather.weather.view.data.response.GeolocationResponse
import dev.tigrao.sweather.weather.view.domain.model.*
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale.getDefault

internal interface FetchWeatherDataByGeoLocationUseCase {
    suspend operator fun invoke(): Result<WeatherLocationModel, WeatherLocationErrorModel>
}

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
        }.transformMap(
            success = {
                convertLocation(it)
            },
            error = {
                WeatherLocationErrorModel.GenericError
            }
        )

    private fun convertLocation(it: GeolocationResponse): WeatherLocationModel {

        val locale = Locale("", it.sys.country)

        return WeatherLocationModel(
            location = LocationModel(
                city = it.name,
                country = locale.country,
                date = "",
            ),
            temperature = TemperatureModel(
                temperature = it.main.temp.toInt().toString(),
                min = it.main.tempMin.toInt().toString(),
                max = it.main.tempMax.toInt().toString(),
                unitType = UnitType.METRIC,
            ),
            // TODO: Move to string values
            weather = WeatherInfoModel(
                condition = it.weather.first().main,
                conditionIcon = it.weather.first().icon,
                humidity = "${it.main.humidity}%",
                pressure = "${it.main.pressure}hPa",
                windSpeed = "${convertWindSpeed(it.wind.speed)}km/h",
                sunrise = dateConverter(it.sys.sunrise),
                sunset = dateConverter(it.sys.sunset),
            )
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
