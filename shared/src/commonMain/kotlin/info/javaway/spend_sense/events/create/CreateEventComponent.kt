package info.javaway.spend_sense.events.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.calendar.CalendarComponent
import info.javaway.spend_sense.calendar.CalendarContract
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.events.create.CreateEventContract.*
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.now
import info.javaway.spend_sense.extensions.updateState
import info.javaway.spend_sense.platform.randomUUID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.koin.core.KoinApplication.Companion.init
class CreateEventComponent(
    context: ComponentContext,
    private val categoriesRepository: CategoriesRepository,
    private val onOutput: (Output) -> Unit
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State.NONE)
    override val state = _state

    private val slotNavigation = SlotNavigation<Config>()
    val slots: Value<ChildSlot<*, Child>> = childSlot(
        source = slotNavigation,
        serializer = Config.serializer(),
        childFactory = ::createChildSlot
    )

    init {
        bindCategoriesIntoState()
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.ChangeCost -> changeCost(event.cost)
            is UiEvent.ChangeNote -> changeNote(event.note)
            is UiEvent.ChangeTitle -> changeTitle(event.title)
            is UiEvent.Finish -> finish()
            is UiEvent.Dismiss -> onOutput(Output.Dismiss)
            is UiEvent.ClickOnCategories -> slotNavigation.activate(Config.CategoriesPicker)
            is UiEvent.ClickOnDate -> slotNavigation.activate(Config.Calendar)
            is UiEvent.SelectCategory -> selectCategory(event.category)
            is UiEvent.DismissCalendar -> slotNavigation.dismiss()
            is UiEvent.DismissCategories -> slotNavigation.dismiss()
        }
    }

    private fun selectDate(date: LocalDate?) {
        slotNavigation.dismiss()
        _state.updateState { copy(date = date ?: LocalDate.now()) }
    }


    private fun changeTitle(title: String) = _state.updateState { copy(title = title) }

    private fun changeNote(note: String) = _state.updateState { copy(note = note) }

    private fun changeCost(cost: String) =
        _state.updateState { copy(cost = cost.toDoubleOrNull() ?: this.cost) }

    private fun selectCategory(category: Category) {
        slotNavigation.dismiss()
        _state.updateState { copy(category = category) }
    }


    private fun bindCategoriesIntoState() {
        doOnCreate {
            categoriesRepository.getAllFlow().onEach {
                _state.updateState { copy(categories = it) }
            }.launchIn(componentScope)
        }
    }

    private fun finish() {
        val spendEvent = with(state.value) {
            val now = LocalDateTime.now()
            SpendEvent(
                id = randomUUID(),
                title = title,
                cost = cost,
                date = date,
                categoryId = category.id,
                createdAt = now,
                updatedAt = now,
                note = note
            )
        }
        onOutput(Output.Finish(spendEvent))
    }

    private fun createChildSlot(
        config: Config,
        context: ComponentContext
    ) = when (config) {
        is Config.Calendar -> Child.Calendar(
            CalendarComponent(context, onOutput = ::onCalendarOutput)
        )
        is Config.CategoriesPicker -> Child.CategoriesPicker
    }

    fun onCalendarOutput(output: CalendarContract.Output) {
        when (output) {
            is CalendarContract.Output.SelectDay -> selectDate(output.day.date)
        }
    }


    class Factory(
        private val categoriesRepository: CategoriesRepository
    ) {
        fun create(context: ComponentContext, onOutput: (Output) -> Unit) =
            CreateEventComponent(
                context,
                categoriesRepository,
                onOutput
            )
    }
}

