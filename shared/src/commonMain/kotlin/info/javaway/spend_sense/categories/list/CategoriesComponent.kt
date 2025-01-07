package info.javaway.spend_sense.categories.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import info.javaway.spend_sense.base.StateHolder
import info.javaway.spend_sense.base.UiEventHandler
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.create.CreateCategoryData
import info.javaway.spend_sense.categories.create.toCategory
import info.javaway.spend_sense.categories.list.CategoriesContract.*
import info.javaway.spend_sense.extensions.componentScope
import info.javaway.spend_sense.extensions.now
import info.javaway.spend_sense.extensions.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import org.koin.core.KoinApplication.Companion.init

class CategoriesComponent(
    context: ComponentContext,
    private val repository: CategoriesRepository
) : ComponentContext by context, StateHolder<State>, UiEventHandler<UiEvent> {

    private val _state = MutableStateFlow(State.NONE)
    override val state = _state

    private val slotNavigation = SlotNavigation<Config>()
    val slots: Value<ChildSlot<*, Child>> = childSlot(
        source = slotNavigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createSlotChild
    )

    init {
        activate()
    }

    override fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.CreateCategory -> {
                slotNavigation.dismiss()
                createCategory(event.data)
            }

            is UiEvent.ShowCreateCategoryDialog -> slotNavigation.activate(Config.CreateCategory)
            is UiEvent.CreateCategoryDialogDismiss -> slotNavigation.dismiss()
        }
    }

    private fun activate() {
        repository.getAllFlow().onEach {
            _state.updateState { copy(categories = it) }
        }.launchIn(componentScope)
    }

    private fun createCategory(data: CreateCategoryData) {
        val now = LocalDateTime.now()
        val category = data.toCategory(now)
        componentScope.launch {
            repository.create(category)
        }
    }

    private fun createSlotChild(config: Config, context: ComponentContext) = when (config) {
        is Config.CreateCategory -> Child.CreateCategory
    }

    class Factory(
        private val repository: CategoriesRepository
    ) {
        fun create(
            context: ComponentContext
        ) = CategoriesComponent(
            context,
            repository
        )
    }
}