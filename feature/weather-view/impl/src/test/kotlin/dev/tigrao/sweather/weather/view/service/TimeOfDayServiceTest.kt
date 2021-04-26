package dev.tigrao.sweather.weather.view.service

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.*

@RunWith(Parameterized::class)
class TimeOfDayServiceTest(val time: Int, val expected: Boolean) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data() = listOf(
            arrayOf(0, true),
            arrayOf(1, true),
            arrayOf(2, true),
            arrayOf(3, true),
            arrayOf(4, true),
            arrayOf(5, true),
            arrayOf(6, false),
            arrayOf(7, false),
            arrayOf(8, false),
            arrayOf(9, false),
            arrayOf(10, false),
            arrayOf(11, false),
            arrayOf(12, false),
            arrayOf(13, false),
            arrayOf(14, false),
            arrayOf(15, false),
            arrayOf(16, false),
            arrayOf(17, false),
            arrayOf(18, false),
            arrayOf(19, true),
            arrayOf(20, true),
            arrayOf(21, true),
            arrayOf(22, true),
            arrayOf(23, true),
        )
    }


    private val subject = TimeOfDayService()

    @Test
    fun isNight_returnIfNight() {
        val param = Calendar.getInstance()
        param.set(Calendar.HOUR_OF_DAY, time)

        val result = subject.isNight(param.timeInMillis)

        assertEquals(expected, result)
    }
}
