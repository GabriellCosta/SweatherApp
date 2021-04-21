package dev.tigrao.sweather.weather.view.data

import dev.tigrao.sweather.weather.view.data.response.GeolocationResponse
import retrofit2.http.GET

internal interface WeatherViewApi {

    @GET("weather?lat={lat}&lon={lon}")
    fun getDataByGeographicCoordinates(lat: Double, lon: Double): GeolocationResponse
}
