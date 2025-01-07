package info.javaway.spend_sense.common.ui.atoms

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import info.javaway.spend_sense.common.ui.theme.AppTheme
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppModalBottomSheetNative(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = BottomSheetDefaults.ExpandedShape,
    containerColor: Color = AppThemeProvider.colors.surface,
    dragHandle: @Composable (() -> Unit)? = { BottomSheetDefaults.DragHandle() },
    windowInsets: WindowInsets = BottomSheetDefaults.windowInsets.only(WindowInsetsSides.Bottom),
    content: @Composable ColumnScope.() -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var previousVisibilityState by remember { mutableStateOf(false) }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
        containerColor = containerColor,
        shape = shape,
        dragHandle = dragHandle,
        contentWindowInsets = { windowInsets },
        content = content
    )

    LaunchedEffect(bottomSheetState) {
        snapshotFlow { bottomSheetState.isVisible }
            .collect { isVisible ->
                if (previousVisibilityState && isVisible.not()) {
                    onDismissRequest()
                }
                previousVisibilityState = isVisible
            }
    }
}