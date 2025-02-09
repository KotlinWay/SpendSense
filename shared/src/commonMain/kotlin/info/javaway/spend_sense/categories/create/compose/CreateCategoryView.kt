package info.javaway.spend_sense.categories.create.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import info.javaway.spend_sense.categories.create.CreateCategoryData
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppModalBottomSheetNative
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.BottomModalContainer
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.save
import spendsense.shared.generated.resources.subtitle_category_placeholder
import spendsense.shared.generated.resources.title_category_placeholder

@Composable
fun CreateCategoryView(
    onDismissRequest: () -> Unit,
    createListener: (CreateCategoryData) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }

    var rColor by remember { mutableFloatStateOf(0.3f) }
    var gColor by remember { mutableFloatStateOf(0.6f) }
    var bColor by remember { mutableFloatStateOf(0.9f) }

    AppModalBottomSheetNative(onDismissRequest = onDismissRequest) {
        Column (
            modifier = Modifier.padding(8.dp).padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AppTextField(
                title,
                stringResource(Res.string.title_category_placeholder),
                modifier = Modifier.fillMaxWidth()
            ) { title = it }

            Spacer(modifier = Modifier.height(16.dp))

            AppTextField(
                subtitle,
                stringResource(Res.string.subtitle_category_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
            ) { subtitle = it }

            Spacer(modifier = Modifier.height(16.dp))

            ColorBox(rColor, gColor, bColor) {
                Column {
                    ColorSelector(Color.Red, rColor) { rColor = it }
                    ColorSelector(Color.Green, gColor) { gColor = it }
                    ColorSelector(Color.Blue, bColor) { bColor = it }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            AppButton(stringResource(Res.string.save)) {
                createListener(
                    CreateCategoryData(
                        title, subtitle, Color(rColor, gColor, bColor).toArgb().toString(16)
                    )
                )
            }
        }
    }
}









