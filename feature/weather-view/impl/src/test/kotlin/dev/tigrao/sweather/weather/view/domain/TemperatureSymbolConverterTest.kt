package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.domain.model.UnitType
import org.junit.Assert.assertEquals
import org.junit.Test

class TemperatureSymbolConverterTest {

    private val subject = TemperatureSymbolConverter()

    @Test
    fun mapFrom_standard_returnKelvin() {
        val result = subject.mapFrom(UnitType.STANDARD)

        assertEquals("K", result)
    }

    @Test
    fun mapFrom_metric_returnCelsius() {
        val result = subject.mapFrom(UnitType.METRIC)

        assertEquals("C", result)
    }

    @Test
    fun mapFrom_imperial_returnFahrenheit() {
        val result = subject.mapFrom(UnitType.IMPERIAL)

        assertEquals("F", result)
    }
}
