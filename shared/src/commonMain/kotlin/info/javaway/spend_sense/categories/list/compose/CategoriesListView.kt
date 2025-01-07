package info.javaway.spend_sense.categories.list.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import info.javaway.spend_sense.categories.model.Category

@Composable
fun CategoriesListView(
    categories: List<Category>,
    onClick: (Category) -> Unit
) {
    LazyColumn {
        items(categories) { category ->
            CategoryItem(category){ onClick(category) }
        }
    }
}