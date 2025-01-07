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
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import info.javaway.spend_sense.common.ui.atoms.AppToast
import info.javaway.spend_sense.common.ui.atoms.RootBox
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.settings.SettingsComponent
import info.javaway.spend_sense.settings.SettingsContract
import info.javaway.spend_sense.settings.SettingsContract.UiEvent.*
import info.javaway.spend_sense.settings.child.auth.compose.AuthView
import info.javaway.spend_sense.settings.child.sync.compose.SyncView
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import spendsense.shared.generated.resources.Res
import spendsense.shared.generated.resources.dark_theme
import spendsense.shared.generated.resources.data_sync_error
import spendsense.shared.generated.resources.data_sync_success

@Composable
fun BoxScope.SettingsScreen(
    component: SettingsComponent
) {

    val state by component.state.collectAsState()
    val stack by component.stack.subscribeAsState()
    val onEvent = component::onEvent
    var showMessage by remember { mutableStateOf<StringResource?>(null) }

    LaunchedEffect(Unit) {
        component.effect.onEach { event ->
            when (event) {
                SettingsContract.Effect.DataSynced -> showMessage = Res.string.data_sync_success
                is SettingsContract.Effect.Error -> Res.string.data_sync_error
            }
        }.launchIn(this)
    }

    RootBox {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {

            when(val child = stack.active.instance){
                is SettingsContract.Child.Auth ->  AuthView(child.component)
                is SettingsContract.Child.Sync ->  SyncView(child.component)
            }

            Row(
                modifier = Modifier.padding(16.dp)
                    .background(AppThemeProvider.colors.surface, RoundedCornerShape(16.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(Res.string.dark_theme), modifier = Modifier.weight(1f),
                    color = AppThemeProvider.colors.onSurface
                )
                Switch(
                    state.themeIsDark, onCheckedChange = { onEvent(SwitchTheme(it)) },
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