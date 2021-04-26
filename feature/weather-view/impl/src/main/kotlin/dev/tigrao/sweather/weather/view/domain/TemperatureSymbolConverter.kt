package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.domain.model.UnitType

internal class TemperatureSymbolConverter {

    fun mapFrom(from: UnitType) = when(from){
        UnitType.STANDARD -> "K"
        UnitType.METRIC -> "C"
        UnitType.IMPERIAL -> "F"
    }
}
