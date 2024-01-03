package info.javaway.spend_sense.events.create

import info.javaway.spend_sense.base.BaseEvent
import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.events.create.CreateEventViewModel.*
import info.javaway.spend_sense.events.model.SpendEvent
import info.javaway.spend_sense.extensions.now
import info.javaway.spend_sense.platform.randomUUID
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

class CreateEventViewModel : BaseViewModel<State, Event>() {

    override fun initialState() = State.NONE

    fun selectDate(date: LocalDate?) = updateState { copy(date = date ?: LocalDate.now()) }
    fun resetState() = updateState { State.NONE }
    fun changeTitle(title: String) = updateState { copy(title = title) }
    fun changeCost(cost: String) = updateState { copy(cost = cost.toDoubleOrNull() ?: this.cost) }
    fun selectCategory(category: Category) = updateState { copy(category = category) }

    fun finish() {
        val spendEvent = with(state.value){
            val now = LocalDateTime.now()
            SpendEvent(
                id = randomUUID(),
                title = title,
                cost = cost,
                date = date,
                categoryId = category.id,
                createdAt = now,
                updatedAt = now
            )
        }
        resetState()
        pushEvent(Event.Finish(spendEvent))
    }


    data class State(
        val title: String,
        val category: Category,
        val date: LocalDate,
        val cost: Double
    ) : BaseViewState {
        companion object {
            val NONE = State(
                title = "",
                category = Category.NONE,
                date = LocalDate.now(),
                cost = 0.0
            )
        }
    }

    sealed interface Event : BaseEvent {
        data class Finish(val spendEvent: SpendEvent) : Event
    }


}