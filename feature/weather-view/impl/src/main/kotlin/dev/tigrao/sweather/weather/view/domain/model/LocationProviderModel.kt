package dev.tigrao.sweather.weather.view.domain.model

internal data class LocationProviderModel(
    val lat: Double,
    val lon: Double,
)

internal sealed class LocationProviderErrorModel {

    object ErrorToGetLocation : LocationProviderErrorModel()
}
