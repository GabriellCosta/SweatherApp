package dev.tigrao.sweather.weather.view.view

import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.FragmentScreen

internal class WeatherViewNavigationDefault(
    private val navigationRouter: Router
) : WeatherViewNavigation {
    override fun navigate() {
        navigationRouter.navigateTo(
            FragmentScreen { WeatherViewFragment() }
        )
    }
}
