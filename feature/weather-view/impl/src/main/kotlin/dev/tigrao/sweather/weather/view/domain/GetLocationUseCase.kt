package dev.tigrao.sweather.weather.view.domain

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderErrorModel
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderModel
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal interface GetLocationUseCase {
    suspend operator fun invoke(): dev.tigrao.sweather.domain.core.Result<LocationProviderModel, LocationProviderErrorModel>
}

internal class GetLocation(
    private val fusedLocation: FusedLocationProviderClient,
) : GetLocationUseCase {
    @SuppressLint("MissingPermission")
    override suspend fun invoke(): dev.tigrao.sweather.domain.core.Result<LocationProviderModel, LocationProviderErrorModel> {
        return suspendCoroutine {
            fusedLocation.lastLocation.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.run {
                        dev.tigrao.sweather.domain.core.Result.Success(
                            LocationProviderModel(
                                lat = latitude,
                                lon = longitude,
                            )
                        )
                    }
                    it.resume(result)
                } else {
                    val result = dev.tigrao.sweather.domain.core.Result.Error(
                        LocationProviderErrorModel.ErrorToGetLocation
                    )

                    it.resume(result)
                }
            }
        }
    }
}
