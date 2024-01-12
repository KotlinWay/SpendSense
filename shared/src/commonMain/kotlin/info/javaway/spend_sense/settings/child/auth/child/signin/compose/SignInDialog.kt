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
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppTextField
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInViewModel
import info.javaway.spend_sense.settings.child.auth.child.signin.model.SignInContract
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun SignInDialog(
    viewModel: SignInViewModel,
    successListener: () -> Unit
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                is SignInContract.Event.Error -> showMessage = event.message
                SignInContract.Event.Success -> successListener()
            }
        }.launchIn(this)
    }

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
                stringResource(MR.strings.email),
                onValueChange = viewModel::changeEmail
            )

            AppTextField(
                state.password,
                stringResource(MR.strings.password),
                onValueChange = viewModel::changePassword
            )

            AppButton(stringResource(MR.strings.login), onClick = viewModel::login)
        }

        AppToast(showMessage) { showMessage = null }
    }

}