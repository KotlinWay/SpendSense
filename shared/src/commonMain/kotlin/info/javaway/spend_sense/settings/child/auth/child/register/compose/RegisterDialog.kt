package info.javaway.spend_sense.settings.child.auth.child.register.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponent
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterContract
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterContract.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.confirm_password
import spendsense.shared.generated.resources.email
import spendsense.shared.generated.resources.password
import spendsense.shared.generated.resources.register

@Composable
fun RegisterDialog(
    component: RegisterComponent
) {

    val state by component.state.collectAsState()
    val onEvent = component::onEvent
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        component.effects.onEach { effect ->
            when (effect) {
                is Effect.Error -> showMessage = effect.message
            }
        }.launchIn(this)
    }
    Dialog(onDismissRequest = { onEvent(UiEvent.Dismiss) }) {
        Box {
            Column(
                modifier = Modifier
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppTextField(
                    state.email,
                    stringResource(Res.string.email),
                    onValueChange = { onEvent(UiEvent.ChangeEmail(it)) }
                )

                AppTextField(
                    state.password,
                    stringResource(Res.string.password),
                    onValueChange = { onEvent(UiEvent.ChangePassword(it)) }
                )

                AppTextField(
                    state.confirmPassword,
                    stringResource(Res.string.confirm_password),
                    onValueChange = { onEvent(UiEvent.ChangeConfirmPassword(it)) }
                )

                AnimatedVisibility(
                    state.sendIsActive,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    AppButton(stringResource(Res.string.register)) { onEvent(UiEvent.Register) }
                }
            }

            AppToast(showMessage) { showMessage = null }
        }
    }
}