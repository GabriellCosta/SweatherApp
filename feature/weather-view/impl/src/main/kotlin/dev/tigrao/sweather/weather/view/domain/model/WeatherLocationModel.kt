package dev.tigrao.sweather.weather.view.domain.model

internal data class WeatherLocationModel(
    val location: LocationModel,
    val temperature: TemperatureModel,
    val weather: WeatherInfoModel
)

internal data class LocationModel(
    val city: String,
    val country: String,
    val date: String,
)

internal data class TemperatureModel(
    val temperature: String,
    val min: String,
    val max: String,
    val unitType: UnitType,
)

internal data class WeatherInfoModel(
    val humidity: String,
    val pressure: String,
    val windSpeed: String,
    val sunrise: String,
    val sunset: String,
)

internal enum class UnitType {
    STANDARD,
    METRIC,
    IMPERIAL,
}
