package dev.tigrao.sweather.weather.view.data

import dev.tigrao.sweather.network.http.Retry
import dev.tigrao.sweather.weather.view.data.response.GeolocationResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface WeatherViewApi {

    @GET("weather?units=metric")
    @Retry
    suspend fun getDataByGeographicCoordinates(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): GeolocationResponse
}
