package dev.tigrao.sweather.weather.view.service

import java.util.*

private const val NIGHT_START = 19
private const val NIGHT_END = 5

private const val HOUR_START_A_DAY = 0
private const val HOUR_END_A_DAY = 23

internal class TimeOfDayService {

    fun isNight(time: Long): Boolean {
        val calendar = Calendar.getInstance()
        calendar.time = Date(time)

        val hour = calendar.get(Calendar.HOUR_OF_DAY)

        return hour in NIGHT_START..HOUR_END_A_DAY || hour in HOUR_START_A_DAY..NIGHT_END
    }
}
