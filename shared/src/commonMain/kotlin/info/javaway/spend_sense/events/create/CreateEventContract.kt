package info.javaway.spend_sense.events.create

import info.javaway.spend_sense.calendar.CalendarComponent
import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.extensions.now
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

interface CreateEventContract {
    data class State(
        val title: String,
        val category: Category,
        val date: LocalDate,
        val cost: Double,
        val note: String,
        val categories: List<Category>
    ) {
        companion object {
            val NONE = State(
                title = "",
                category = Category.NONE,
                date = LocalDate.now(),
                cost = 0.0,
                note = "",
                categories = emptyList()
            )
        }
    }

    sealed interface UiEvent {
        data object Finish : UiEvent
        data object Dismiss : UiEvent
        data object ClickOnCategories : UiEvent
        data object ClickOnDate : UiEvent
        data object DismissCalendar : UiEvent
        data object DismissCategories : UiEvent
        class SelectCategory(val category: Category) : UiEvent
        class ChangeNote(val note: String) : UiEvent
        class ChangeCost(val cost: String) : UiEvent
        class ChangeTitle(val title: String) : UiEvent
    }

    sealed interface Output {
        class Finish(val event: SpendEvent) : Output
        data object Dismiss : Output
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object Calendar : Config

        @Serializable
        data object CategoriesPicker : Config
    }

    sealed interface Child {
        class Calendar(val component: CalendarComponent) : Child
        data object CategoriesPicker : Child
    }
}