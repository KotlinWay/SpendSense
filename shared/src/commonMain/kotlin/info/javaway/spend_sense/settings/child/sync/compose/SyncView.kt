package info.javaway.spend_sense.settings.child.sync.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.format
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppCard
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider

@Composable
fun SyncView(
    email: String,
    isLoading: Boolean,
    syncListener: () -> Unit,
    logoutListener: () -> Unit
) {

    AppCard {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            Column {
                Text(
                    MR.strings.auth_info.format(email).localized(),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp
                )
                AppButton(
                    stringResource(MR.strings.sync),
                    Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    onClick = syncListener
                )
                AppButton(
                    stringResource(MR.strings.logout),
                    Modifier.fillMaxWidth().padding(16.dp),
                    onClick = logoutListener
                )
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()
                    .background(AppThemeProvider.colors.background.copy(0.5f))
                    .clickable { }
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AppThemeProvider.colors.accent
                    )
                }
            }
        }
    }

}