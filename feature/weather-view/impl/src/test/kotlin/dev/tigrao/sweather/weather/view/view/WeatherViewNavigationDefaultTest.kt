package dev.tigrao.sweather.weather.view.view

import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class WeatherViewNavigationDefaultTest {

    val navigationRouter = mockk<Router>()
    private val subject = WeatherViewNavigationDefault(navigationRouter)

    private val slot = slot<FragmentScreen>()

    init {
        every { navigationRouter.navigateTo(any()) } just runs
    }

    @Test
    fun navigate_callNavigationRouter() {
        subject.navigate()

        verify(exactly = 1) {
            navigationRouter.navigateTo(any())
        }
    }

    @Test
    fun navigate_returnCorrectFragmentClass() {
        subject.navigate()

        verify(exactly = 1) {
            navigationRouter.navigateTo(capture(slot))
        }

        val factory = FragmentFactory()

        assertEquals(
            FragmentScreen { WeatherViewFragment() }.createFragment(factory).javaClass.name,
            slot.captured.createFragment(factory).javaClass.name
        )
    }
}
