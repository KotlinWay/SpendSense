package info.javaway.spend_sense.calendar

import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.calendar.model.CalendarMonth
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate

interface CalendarContract {
    data class State(
        val months: List<CalendarMonth>,
        val selectedDay: CalendarDay?,
        val firstDayIsMonday: Boolean,
        val labels: Map<CalendarMonth, List<CalendarLabel>>,
        val selectedMonth: CalendarMonth,
    ) {

        companion object {
            val NONE = State(
                months = emptyList(),
                selectedDay = null,
                firstDayIsMonday = false,
                labels = emptyMap(),
                selectedMonth = CalendarMonth.fromDate(LocalDate.now())
            )
        }
    }

    sealed interface UiEvent {
        data object PrevMonth : UiEvent
        data object NextMonth : UiEvent
        class UpdateYear(val year: Int) : UiEvent
        class UpdateLabels(val labels: List<CalendarLabel>) : UiEvent
    }

    sealed interface Output{
        data class SelectDay(val day: CalendarDay) : Output
    }
}