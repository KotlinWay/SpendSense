package info.javaway.spend_sense.events.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.calendar.CalendarComponent
import info.javaway.spend_sense.calendar.CalendarContract
import info.javaway.spend_sense.calendar.model.CalendarDay
import info.javaway.spend_sense.calendar.model.CalendarLabel
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.create.CreateEventComponent
import info.javaway.spend_sense.events.create.CreateEventContract
import info.javaway.spend_sense.events.list.EventsContract.*
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.events.model.toCalendarLabel
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class EventsComponent(
    context: ComponentContext,
    private val eventsRepository: EventsRepository,
    private val categoriesRepository: CategoriesRepository,
    private val createEventComponentFactory: CreateEventComponent.Factory
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State.NONE)
    override val state = _state.asStateFlow()

    private val slotNavigation = SlotNavigation<Config>()
    val slots: Value<ChildSlot<*, Child>> = childSlot(
        source = slotNavigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createChildSlots
    )

    val calendarComponent = CalendarComponent(
        context = childContext("calendarComponent"),
        onOutput = ::onCalendarOutput
    )

    private fun onCalendarOutput(output: CalendarContract.Output) {
        when (output) {
            is CalendarContract.Output.SelectDay -> selectDay(output.day)
        }
    }

    init {
        doOnCreate { activate() }
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.CreateEvent -> createEvent(event.spendEvent)
            is UiEvent.ShowCreateEventDialog -> slotNavigation.activate(Config.CreateEvent)
        }
    }

    private fun selectDay(day: CalendarDay) {
        _state.updateState { copy(selectedDay = day) }

    }

    private fun createEvent(newEvent: SpendEvent) {
        componentScope.launch { eventsRepository.create(newEvent) }
    }

    private fun activate() {
        combine(
            eventsRepository.getAllFlow(),
            categoriesRepository.getAllFlow()
        ) { events, categories ->
            val labels = mapEventsCategoriesToLabels(events, categories)
            calendarComponent.onEvent(CalendarContract.UiEvent.UpdateLabels(labels))
            _state.updateState {
                copy(
                    events = events,
                    categories = categories,
                )
            }
        }.launchIn(componentScope)
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

    private fun createChildSlots(
        config: Config,
        context: ComponentContext
    ) = when (config) {
        Config.CreateEvent -> Child.CreateEvent(
            createEventComponentFactory.create(context, ::onCreateEventOutput)
        )
    }

    private fun onCreateEventOutput(output: CreateEventContract.Output) {
        when (output) {
            is CreateEventContract.Output.Dismiss -> slotNavigation.dismiss()
            is CreateEventContract.Output.Finish -> handleNewEvent(output.event)
        }
    }

    private fun handleNewEvent(event: SpendEvent) {
        slotNavigation.dismiss()
        componentScope.launch {
            eventsRepository.create(event)
        }
    }

    class Factory(
        private val eventsRepository: EventsRepository,
        private val categoriesRepository: CategoriesRepository,
        private val createEventComponentFactory: CreateEventComponent.Factory
    ) {
        fun create(context: ComponentContext) = EventsComponent(
            context, eventsRepository, categoriesRepository, createEventComponentFactory
        )
    }
}