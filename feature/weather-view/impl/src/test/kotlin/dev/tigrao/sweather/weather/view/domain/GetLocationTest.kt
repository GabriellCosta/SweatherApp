package dev.tigrao.sweather.weather.view.domain

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import dev.tigrao.sweather.domain.core.Result
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderErrorModel
import dev.tigrao.sweather.weather.view.domain.model.LocationProviderModel
import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

internal class GetLocationTest {

    val fusedLocation = mockk<FusedLocationProviderClient>()
    private val getLocationUseCase = GetLocation(fusedLocation)

    @Test
    fun invoke_hasLastLocation_returnIt() = runBlocking {
        prepareScenario(
            lastLocationTask = true,
            lastLocation = mockk() {
                every { latitude } returns 13.0
                every { longitude } returns 17.0
            }
        )
        val result = getLocationUseCase()

        val expected = Result.Success(
            LocationProviderModel(
                lat = 13.0,
                lon = 17.0,
            )
        )

        assertEquals(expected, result)
    }

    @Test
    fun invoke_notHasLastLocationAndLocationNotAvailable_returnErrorModel() = runBlocking {
        prepareScenario(
            lastLocationTask = false,
            isAvailable = false,
        )
        val result = getLocationUseCase()

        val expected = Result.Error(LocationProviderErrorModel.ErrorToGetLocation)

        assertEquals(expected, result)
    }

    @Test
    fun invoke_notHasLastLocationAndLocationNotAvailable_removeCallback() = runBlocking {
        prepareScenario(
            lastLocationTask = false,
            isAvailable = false,
        )
        getLocationUseCase()

        verify(exactly = 1) { fusedLocation.removeLocationUpdates(any<LocationCallback>()) }
    }

    @Test
    fun invoke_notHasLastLocationAndLocationAvailable_removeCallback() = runBlocking {
        prepareScenario(
            lastLocationTask = false,
            isAvailable = true,
            lastLocation = mockk {
                every { latitude } returns 13.0
                every { longitude } returns 17.0
            }
        )
        getLocationUseCase()

        verify(exactly = 1) { fusedLocation.removeLocationUpdates(any<LocationCallback>()) }
    }

    @Test
    fun invoke_notHasLastLocationAndLocationAvailable_returnIt() = runBlocking {
        prepareScenario(
            lastLocationTask = false,
            isAvailable = true,
            lastLocation = mockk {
                every { latitude } returns 13.0
                every { longitude } returns 17.0
            }
        )
        val result = getLocationUseCase()

        val expected = Result.Success(
            LocationProviderModel(
                lat = 13.0,
                lon = 17.0,
            )
        )

        assertEquals(expected, result)
    }

    private fun prepareScenario(
        lastLocationTask: Boolean = false,
        lastLocation: Location? = null,
        isAvailable: Boolean? = false,
    ) {
        val taskLocation = mockk<Task<Location>>()
        val slot = CapturingSlot<OnCompleteListener<Location>>()

        every { taskLocation.addOnCompleteListener(capture(slot)) } answers {
            every { taskLocation.result } returns lastLocation
            every { taskLocation.isSuccessful } returns lastLocationTask
            slot.captured.onComplete(taskLocation)

            taskLocation
        }

        every { fusedLocation.lastLocation } returns taskLocation

        val slotCallback = CapturingSlot<LocationCallback>()
        every {
            fusedLocation.requestLocationUpdates(
                any(),
                capture(slotCallback),
                any()
            )
        } answers {
            isAvailable?.let {
                slotCallback.captured.onLocationAvailability(mockk() {
                    every { isLocationAvailable } returns it
                })
            }

            lastLocation?.let {
                slotCallback.captured.onLocationResult(LocationResult.create(listOf(it)))
            }

            mockk()
        }

        every { fusedLocation.removeLocationUpdates(any<LocationCallback>()) } returns mockk()
    }
}
