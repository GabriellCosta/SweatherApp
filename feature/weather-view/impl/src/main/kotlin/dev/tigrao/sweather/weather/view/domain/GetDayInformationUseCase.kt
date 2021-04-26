package dev.tigrao.sweather.weather.view.domain

import dev.tigrao.sweather.weather.view.domain.model.DayInformationModel
import dev.tigrao.sweather.weather.view.service.TimeOfDayService
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_PATTERN = "E, dd MMM yyyy | HH:mm"

internal interface GetDayInformationUseCase {
    operator fun invoke(): DayInformationModel
}

internal class GetDayInformation(
    private val timeOfDayService: TimeOfDayService,
) : GetDayInformationUseCase {
    override fun invoke(): DayInformationModel {
        val calendar = Calendar.getInstance()
        val format = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())

        val time = calendar.time

        val formattedDate = format.format(time)

        return DayInformationModel(
            date = formattedDate,
            isDay = !timeOfDayService.isNight(time.time),
        )
    }
}
