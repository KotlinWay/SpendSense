package info.javaway.spend_sense.categories.list.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.spend_sense.categories.create.compose.CreateCategoryView
import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.categories.list.CategoriesContract.Child
import info.javaway.spend_sense.categories.list.CategoriesContract.UiEvent.CreateCategory
import info.javaway.spend_sense.categories.list.CategoriesContract.UiEvent.CreateCategoryDialogDismiss
import info.javaway.spend_sense.categories.list.CategoriesContract.UiEvent.ShowCreateCategoryDialog
import info.javaway.spend_sense.common.ui.atoms.FAB
import info.javaway.spend_sense.common.ui.atoms.RootBox

@Composable
fun CategoriesScreen(
    component: CategoriesComponent
) {

    val state by component.state.collectAsState()
    val slots by component.slots.subscribeAsState()
    val onEvent = component::onEvent

    RootBox {
        CategoriesListView(state.categories) { }
        FAB { onEvent(ShowCreateCategoryDialog) }
    }

    when(slots.child?.instance){
        is Child.CreateCategory -> CreateCategoryView(
            onDismissRequest = { onEvent(CreateCategoryDialogDismiss) },
            createListener = { data -> onEvent(CreateCategory(data)) }
        )
        null -> Unit
    }
}