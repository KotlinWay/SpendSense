package info.javaway.spend_sense.settings.child.auth.child.signin.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInComponent
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInContract
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInContract.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.email
import spendsense.shared.generated.resources.login
import spendsense.shared.generated.resources.password

@Composable
fun SignInDialog(component: SignInComponent) {

    val state by component.state.collectAsState()
    val onEvent = component::onEvent
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        component.effects.onEach { event ->
            when (event) {
                is Effect.Error -> showMessage = event.message
            }
        }.launchIn(this)
    }
    Dialog(onDismissRequest = { onEvent(UiEvent.Dismiss)}){
        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(
                        AppThemeProvider.colors.surface,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(16.dp)
            ) {
                AppTextField(
                    state.email,
                    stringResource(Res.string.email),
                    onValueChange = { onEvent(UiEvent.ChangeEmail(it)) }
                )

                AppTextField(
                    state.password,
                    stringResource(Res.string.password),
                    onValueChange =  { onEvent(UiEvent.ChangePassword(it)) }
                )

                AppButton(stringResource(Res.string.login))  { onEvent(UiEvent.Login) }
            }

            AppToast(showMessage) { showMessage = null }
        }
    }

}