@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class)

package info.javaway.spend_sense.categories.list.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import info.javaway.spend_sense.categories.create.compose.CreateCategoryView
import info.javaway.spend_sense.categories.list.CategoriesViewModel
import info.javaway.spend_sense.common.ui.atoms.FAB
import info.javaway.spend_sense.common.ui.atoms.RootBox
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel
) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden, skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetContent = {
            CreateCategoryView(
                isExpand = sheetState.currentValue == ModalBottomSheetValue.Expanded
            ) { data ->
                scope.launch { sheetState.hide() }
                viewModel.createCategory(data)
            }
        },
        sheetState = sheetState,
        sheetBackgroundColor = Color.Transparent,
        modifier = Modifier.zIndex(1f)
    ) {
        RootBox {
            CategoriesListView(viewModel, Modifier.fillMaxSize().padding(8.dp)) {

            }
            FAB { scope.launch { sheetState.show() } }
        }
    }
}







