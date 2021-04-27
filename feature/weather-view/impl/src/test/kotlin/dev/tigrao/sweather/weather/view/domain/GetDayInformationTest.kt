package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.domain.model.DayInformationModel
import dev.tigrao.sweather.weather.view.service.TimeOfDayService
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.math.exp

class GetDayInformationTest {

    private val timeOfDayService = mockk<TimeOfDayService>()
    private val subject = GetDayInformation(timeOfDayService)

    @Before
    fun setup() {
        Locale.setDefault(Locale("pt", "BR"))
    }

    @Test
    fun invoke_returnConvertModel() {
        val calendar = Calendar.getInstance().apply {
            this.set(Calendar.YEAR, 2020)
            this.set(Calendar.MONTH, 4)
            this.set(Calendar.DAY_OF_MONTH, 25)
            this.set(Calendar.HOUR_OF_DAY, 9)
            this.set(Calendar.MINUTE, 25)
        }

        prepareScenario(
            isNight = true,
            calendar = calendar
        )

        val result = subject()

        val expected = DayInformationModel(
            date = "Seg, 25 mai 2020 | 09:25",
            isDay = false
        )

        assertEquals(expected, result)
    }

    fun prepareScenario(
        isNight: Boolean,
        calendar: Calendar,
    ) {
        every { timeOfDayService.isNight(any()) } returns isNight

        mockkStatic(Calendar::class)
        every { Calendar.getInstance() } returns calendar
    }
}
