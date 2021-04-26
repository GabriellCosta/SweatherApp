package dev.tigrao.sweather.weather.view.domain

import android.annotation.SuppressLint
import android.location.Location
import com.google.android.gms.location.*
import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderErrorModel
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderModel
import kotlin.coroutines.Continuation
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

            fusedLocation.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    it.resume(
                        Result.Success(
                            convertLocation(
                                task.result
                            )
                        )
                    )
                } else {
                    requestLocationUpdate(it)
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUpdate(
        it: Continuation<Result<LocationProviderModel, LocationProviderErrorModel>>
    ) {
        val request = LocationRequest.create().apply {
            interval = INTERVAL
            fastestInterval = FASTER_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        fusedLocation.requestLocationUpdates(request, object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                super.onLocationResult(result)

                fusedLocation.removeLocationUpdates(this)
                result?.let { locationResult ->
                    it.resume(
                        Result.Success(
                            convertLocation(
                                locationResult.lastLocation
                            )
                        )
                    )
                } ?: run {
                    it.resume(createErrorLocationResult())
                }
            }

            override fun onLocationAvailability(availability: LocationAvailability?) {
                super.onLocationAvailability(availability)

                if (availability != null && !availability.isLocationAvailable) {
                    it.resume(createErrorLocationResult())
                    fusedLocation.removeLocationUpdates(this)
                }

            }
        }, null)
    }

    private fun convertLocation(location: Location) =
        LocationProviderModel(
            lat = location.latitude,
            lon = location.longitude,
        )

    private fun createErrorLocationResult() = Result.Error(
        LocationProviderErrorModel.ErrorToGetLocation
    )
}
