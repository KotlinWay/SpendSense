package info.javaway.spend_sense.categories

import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.extensions.appLog
import kotlinx.coroutines.flow.flow

class CategoriesRepository {

    fun getAllFlow() = flow { emit(Category.getStubs()) }

    suspend fun create(category: Category){
        appLog("created category: $category")
    }
}