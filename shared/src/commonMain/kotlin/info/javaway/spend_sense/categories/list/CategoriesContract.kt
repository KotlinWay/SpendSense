package info.javaway.spend_sense.categories.list

import info.javaway.spend_sense.categories.create.CreateCategoryData
import info.javaway.spend_sense.categories.model.Category
import kotlinx.serialization.Serializable

interface CategoriesContract {


    data class State(
        val categories: List<Category>
    ) {

        companion object {
            val NONE = State(emptyList())
        }
    }

    sealed interface UiEvent {
        class CreateCategory(val data: CreateCategoryData) : UiEvent
        data object ShowCreateCategoryDialog : UiEvent
        data object CreateCategoryDialogDismiss : UiEvent
    }

    @Serializable
    sealed interface Config {
        @Serializable
        data object CreateCategory : Config
    }

    sealed interface Child {
        data object CreateCategory : Child
    }
}