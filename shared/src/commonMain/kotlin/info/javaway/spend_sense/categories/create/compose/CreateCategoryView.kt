package info.javaway.spend_sense.categories.create.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.categories.create.CreateCategoryData
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.BottomModalContainer

@Composable
fun CreateCategoryView(
    isExpand: Boolean,
    createListener: (CreateCategoryData) -> Unit
) {

    val focusRequester by remember { mutableStateOf(FocusRequester()) }
    val focusManager = LocalFocusManager.current

    var title by remember { mutableStateOf("") }
    var subtitle by remember { mutableStateOf("") }

    var rColor by remember { mutableFloatStateOf(0.3f) }
    var gColor by remember { mutableFloatStateOf(0.6f) }
    var bColor by remember { mutableFloatStateOf(0.9f) }

    LaunchedEffect(isExpand){
        if(!isExpand){
            focusManager.clearFocus()
            title = ""
            subtitle = ""
            rColor = 0.3f
            gColor = 0.6f
            bColor = 0.9f
        } else {
            focusRequester.requestFocus()
        }
    }

    BottomModalContainer {
        AppTextField(
            title,
            stringResource(MR.strings.title_category_placeholder),
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        ){ title = it}

        Spacer(modifier = Modifier.height(16.dp))

        AppTextField(
            subtitle,
            stringResource(MR.strings.subtitle_category_placeholder),
            modifier = Modifier
                .fillMaxWidth()
        ){ subtitle = it}

        Spacer(modifier = Modifier.height(16.dp))

        ColorBox(rColor, gColor, bColor){
            Column {
                ColorSelector(Color.Red, rColor) { rColor = it}
                ColorSelector(Color.Green, gColor) { gColor = it}
                ColorSelector(Color.Blue, bColor) { bColor = it}
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        AppButton(stringResource(MR.strings.save)){
            createListener(
                CreateCategoryData(
                    title, subtitle, Color(rColor, gColor, bColor).toArgb().toString(16)
                )
            )
        }
    }
}









