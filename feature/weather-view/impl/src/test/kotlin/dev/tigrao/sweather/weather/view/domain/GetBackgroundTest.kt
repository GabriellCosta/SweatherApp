package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.R
import org.junit.Assert.*
import org.junit.Test

class GetBackgroundTest {

    private val subject = GetBackground()

    @Test
    fun invoke_true_returnDayDrawableRes() {
        val expected = R.drawable.bg_weather_view_day

        val result = subject(true)

        assertEquals(expected, result)
    }

    @Test
    fun invoke_false_returnNightDrawableRes() {
        val expected = R.drawable.bg_weather_view_night

        val result = subject(false)

        assertEquals(expected, result)
    }
}
