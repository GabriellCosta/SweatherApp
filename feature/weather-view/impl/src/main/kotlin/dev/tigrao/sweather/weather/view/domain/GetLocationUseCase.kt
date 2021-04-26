package dev.tigrao.sweather.weather.view.domain

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderErrorModel
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val INTERVAL = 6000L
private const val FASTER_INTERVAL = 5000L

internal interface GetLocationUseCase {
    suspend operator fun invoke(): Result<LocationProviderModel, LocationProviderErrorModel>
}

internal class GetLocation(
    private val fusedLocation: FusedLocationProviderClient,
) : GetLocationUseCase {
    @SuppressLint("MissingPermission")
    override suspend fun invoke(): Result<LocationProviderModel, LocationProviderErrorModel> {
        return suspendCoroutine {

            val request = LocationRequest.create().apply {
                interval = INTERVAL
                fastestInterval = FASTER_INTERVAL
                priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            }

            fusedLocation.requestLocationUpdates(request, object : LocationCallback() {
                override fun onLocationResult(result: LocationResult?) {
                    super.onLocationResult(result)

                    result?.let { locationResult ->
                        it.resume(
                            Result.Success(
                                LocationProviderModel(
                                    lat = locationResult.lastLocation.latitude,
                                    lon = locationResult.lastLocation.longitude,
                                )
                            )
                        )
                    } ?: run {
                        it.resume(createErrorLocationResult())
                    }
                }
            }, null)
        }
    }

    private fun createErrorLocationResult() = Result.Error(
        LocationProviderErrorModel.ErrorToGetLocation
    )
}
