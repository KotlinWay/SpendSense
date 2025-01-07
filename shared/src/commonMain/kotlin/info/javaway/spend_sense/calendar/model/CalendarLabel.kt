package info.javaway.spend_sense.calendar.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CalendarLabel(
    val id: String,
    val colorHex: String?,
    val localDate: LocalDate
)