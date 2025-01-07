package info.javaway.spend_sense.calendar.model

import info.javaway.spend_sense.calendar.extensions.initValue
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CalendarDay(
    val selectable: Boolean,
    val isSelected: Boolean,
    val date: LocalDate,
    val labels: List<CalendarLabel>,
    val isStub: Boolean
) {

    val isToday: Boolean
        get() = date == LocalDate.now()

    val number: Int
        get() = date.dayOfMonth

    companion object {
        val NONE = CalendarDay(
            selectable = true,
            isSelected = false,
            date = LocalDate.initValue,
            labels = emptyList(),
            isStub = true
        )
    }
}