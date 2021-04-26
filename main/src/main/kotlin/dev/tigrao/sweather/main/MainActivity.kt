package dev.tigrao.sweather.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import dev.tigrao.sweather.weather.view.view.WeatherViewNavigation
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val appNavigator = AppNavigator(this, R.id.main_container)
    private val navigationRouter by inject<NavigatorHolder>()
    private val weatherViewNavigation by inject<WeatherViewNavigation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.Theme_MyApp)

        weatherViewNavigation.navigate()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationRouter.setNavigator(appNavigator)
    }

    override fun onPause() {
        navigationRouter.removeNavigator()
        super.onPause()
    }
}
