package info.javaway.spend_sense.events.list

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.common.ui.calendar.model.CalendarDay
import info.javaway.spend_sense.common.ui.calendar.model.CalendarLabel
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.list.EventsViewModel.*
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.events.model.SpendEventUI
import info.javaway.spend_sense.events.model.toCalendarLabel
import info.javaway.spend_sense.events.model.toUI
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class EventsViewModel(
    private val eventsRepository: EventsRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel<State, Nothing>() {

    override fun initialState() = State.NONE

    init {
        activate()
    }

    fun selectDay(day: CalendarDay) {
        updateState { copy(selectedDay = day) }
    }

    fun createEvent(newEvent: SpendEvent) {
        viewModelScope.launch { eventsRepository.create(newEvent) }
    }

    private fun activate() {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow()
        ) { events, categories ->
            val labels = mapEventsCategoriesToLabels(events, categories)
            updateState {
                copy(
                    events = events,
                    categories = categories,
                    calendarLabels = labels
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun mapEventsCategoriesToLabels(
        events: List<SpendEvent>,
        categories: List<Category>
    ): List<CalendarLabel> {
        return events.map { event ->
            val category = categories.firstOrNull {
                it.id == event.categoryId
            } ?: Category.NONE
            event.toCalendarLabel(category)
        }
    }
    data class State(
        val selectedDay: CalendarDay?,
        val events: List<SpendEvent>,
        val categories: List<Category>,
        val calendarLabels: List<CalendarLabel>
    ) : BaseViewState {

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
                calendarLabels = emptyList()
            )
        }
    }
}







