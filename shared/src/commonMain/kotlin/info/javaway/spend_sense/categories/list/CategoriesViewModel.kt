package info.javaway.spend_sense.categories.list

import info.javaway.spend_sense.base.BaseViewModel
import info.javaway.spend_sense.base.BaseViewState
import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.create.CreateCategoryData
import info.javaway.spend_sense.categories.create.toCategory
import info.javaway.spend_sense.categories.list.CategoriesViewModel.*
import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.extensions.now
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class CategoriesViewModel (
    private val repository: CategoriesRepository
) : BaseViewModel<State, Nothing>(){

    override fun initialState() = State.NONE

    init {
        activate()
    }

    private fun activate(){
        repository.getAllFlow().onEach {
            updateState { copy(categoties = it) }
        }.launchIn(viewModelScope)
    }

    fun createCategory(data: CreateCategoryData){
        val now = LocalDateTime.now()
        val category = data.toCategory(now)
        viewModelScope.launch {
            repository.create(category)
        }
    }

    data class State(
        val categoties: List<Category>
    ) : BaseViewState {

        companion object {
            val NONE = State(emptyList())
        }
    }
}