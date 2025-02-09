package info.javaway.spend_sense.calendar.month_page

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnResume
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.calendar.model.CalendarMonth
import info.javaway.spend_sense.calendar.month_page.MonthPageContract.*
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.updateState
import io.github.aakira.napier.Napier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MonthPageComponent(
    context: ComponentContext,
    selectedDay: CalendarDay?,
    firstDayIsMonday: Boolean,
    val month: CalendarMonth,
    private val labels: List<CalendarLabel>,
    private val onOutput: (Output) -> Unit
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State(
        calendarMonth = month,
        weeks = month.getWeeks(firstDayIsMonday, selectedDay, labels),
        firstDayIsMonday = firstDayIsMonday
    ))
    override val state = _state

    init {
        doOnResume { onOutput(Output.SelectMonth(month)) }
    }

    override fun onEvent(event: UiEvent) {
        when(event){
            is UiEvent.SelectDay -> selectDay(event.day)
        }
    }

    private fun selectDay(day: CalendarDay) {
        onOutput(Output.SelectDay(day))
        updateWeeks(day)
    }

    private fun updateWeeks(selectedDay: CalendarDay?) {
        componentScope.launch(Dispatchers.Default) {
            with(state.value) {
                val weeks = calendarMonth.getWeeks(firstDayIsMonday, selectedDay, labels)
                _state.updateState { copy(weeks = weeks) }
            }
        }
    }
}