package info.javaway.spend_sense.events.list

import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.events.create.CreateEventComponent
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.events.model.SpendEventUI
import info.javaway.spend_sense.events.model.toUI
import kotlinx.serialization.Serializable

interface EventsContract {

    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
    ) {

        val eventsByDay: List<SpendEventUI>
            get() = events.filter { it.date == selectedDay?.date }
                .map { spendEvent ->
                    spendEvent.toUI(
                        categories.firstOrNull { it.id == spendEvent.categoryId } ?: Category.NONE
                    )
                }

        companion object {
            val NONE = State(
                selectedDay = null,
                events = emptyList(),
                categories = emptyList(),
            )
        }
    }

    sealed interface UiEvent {
        data class CreateEvent(val spendEvent: SpendEvent) : UiEvent
        data object ShowCreateEventDialog : UiEvent
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object CreateEvent : Config
    }

    sealed interface Child {
        class CreateEvent(val component: CreateEventComponent) : Child
    }
}