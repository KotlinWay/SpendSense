package info.javaway.spend_sense.categories

import info.javaway.spend_sense.categories.model.Category
import info.javaway.spend_sense.categories.model.CategoryDao

class CategoriesRepository(
    private val dao: CategoryDao
) {

    fun getAllFlow() = dao.getAllFlow()

    suspend fun getAll() = dao.getAll()

    suspend fun insertAll(categories: List<Category>) = dao.insertAll(categories)

    suspend fun create(category: Category) = dao.insert(category)
}