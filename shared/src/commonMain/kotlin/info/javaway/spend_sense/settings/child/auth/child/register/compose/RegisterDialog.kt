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
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterViewModel
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun RegisterDialog(
    viewModel: RegisterViewModel,
    successListener: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is RegisterContract.Event.Error -> showMessage = event.message
                RegisterContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

    Box {

        Column(
            modifier = Modifier
                .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppTextField(
                state.email,
                stringResource(MR.strings.email),
                onValueChange = viewModel::changeEmail
            )

            AppTextField(
                state.password,
                stringResource(MR.strings.password),
                onValueChange = viewModel::changePassword
            )

            AppTextField(
                state.confirmPassword,
                stringResource(MR.strings.confirm_password),
                onValueChange = viewModel::changeConfirmPassword
            )

            AnimatedVisibility(
                state.sendIsActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                AppButton(stringResource(MR.strings.register), onClick = viewModel::register)
            }
        }

        AppToast(showMessage) { showMessage = null }
    }
}