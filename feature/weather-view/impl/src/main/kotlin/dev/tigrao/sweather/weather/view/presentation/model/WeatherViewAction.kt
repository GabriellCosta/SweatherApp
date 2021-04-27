package dev.tigrao.sweather.weather.view.presentation.model

internal sealed class WeatherViewAction {

    object PermissionGranted : WeatherViewAction()
    object PermissionNotGranted : WeatherViewAction()
    object CheckPermission: WeatherViewAction()
    object TryAgain : WeatherViewAction()
}
