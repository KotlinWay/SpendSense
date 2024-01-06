package info.javaway.spend_sense.categories

import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.categories.model.CategoryDao
import info.javaway.spend_sense.extensions.appLog
import kotlinx.coroutines.flow.flow

class CategoriesRepository(
    private val dao: CategoryDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun create(category: Category)  = dao.insert(category)
}