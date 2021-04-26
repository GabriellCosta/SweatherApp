package dev.tigrao.sweather.weather.view.presentation.model

internal sealed class WeatherViewAction {

    object PermissionGranted : WeatherViewAction()
    object PermissionNotGranted : WeatherViewAction()
    object TryAgain : WeatherViewAction()
}
