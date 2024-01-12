package info.javaway.spend_sense.settings.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.atoms.RootBox
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.SettingsContract
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.settings.child.auth.compose.AuthView
import info.javaway.spend_sense.settings.child.sync.compose.SyncView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun BoxScope.SettingsScreen(
    viewModel: SettingsViewModel
) {

    val state by viewModel.state.collectAsState()
    var showMessage by remember { mutableStateOf<StringResource?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.onEach { event ->
            when (event) {
                SettingsContract.Event.DataSynced -> showMessage = MR.strings.data_sync_success
                is SettingsContract.Event.Error -> MR.strings.data_sync_error
            }
        }.launchIn(this)
    }

    RootBox {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

            if (state.isAuth) {
                SyncView(
                    email = state.email,
                    isLoading = state.isLoading,
                    syncListener = { viewModel.sync() },
                    logoutListener = { viewModel.logout() }
                )
            } else {
                AuthView { viewModel.sync() }
            }



            Row(
                modifier = Modifier.padding(16.dp)
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(MR.strings.dark_theme), modifier = Modifier.weight(1f),
                    color = AppThemeProvider.colors.onSurface
                )
                Switch(
                    state.themeIsDark, onCheckedChange = { viewModel.switchTheme(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = AppThemeProvider.colors.accent,
                        uncheckedTrackColor = AppThemeProvider.colors.onSurface.copy(0.5f),
                        checkedTrackColor = AppThemeProvider.colors.accent.copy(0.5f),
                    )
                )
            }

            DeviceInfoView(state.info)
        }

        AppToast(showMessage?.let { stringResource(it) }) { showMessage = null }
    }

}