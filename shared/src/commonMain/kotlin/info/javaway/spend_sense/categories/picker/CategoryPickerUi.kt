package info.javaway.spend_sense.categories.picker

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import info.javaway.spend_sense.categories.list.compose.CategoriesListView
import info.javaway.spend_sense.categories.model.Category

@Composable
fun CategoryPickerUi(
    categories: List<Category>,
    onDismiss: () -> Unit,
    selectCategory: (Category) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        CategoriesListView(categories, selectCategory)
    }
}