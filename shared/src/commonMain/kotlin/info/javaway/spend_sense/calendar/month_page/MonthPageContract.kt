package info.javaway.spend_sense.calendar.month_page

import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.calendar.model.CalendarMonth
import info.javaway.spend_sense.calendar.model.CalendarWeek
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate

interface MonthPageContract {

    data class State(
        val firstDayIsMonday: Boolean,
        val weeks: List<CalendarWeek>,
        val calendarMonth: CalendarMonth,
    ) {
        val fullSizeWeeks: List<CalendarWeek>
            get() = weeks.let {
                return if (it.size == CalendarWeek.MAX_WEEKS) {
                    it
                } else {
                    it + List(CalendarWeek.MAX_WEEKS - it.size) { CalendarWeek.EMPTY_WEEK }
                }
            }
    }

    sealed interface UiEvent {
        class SelectDay(val day: CalendarDay) : UiEvent
    }

    sealed interface Output {
        data class SelectMonth(val month: CalendarMonth) : Output
        data class SelectDay(val day: CalendarDay) : Output
    }

}