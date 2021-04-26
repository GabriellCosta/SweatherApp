package dev.tigrao.sweather.weather.view.domain.model

internal sealed class WeatherLocationErrorModel {

    object GenericError : WeatherLocationErrorModel()

    object FailToGetLocation : WeatherLocationErrorModel()
}
