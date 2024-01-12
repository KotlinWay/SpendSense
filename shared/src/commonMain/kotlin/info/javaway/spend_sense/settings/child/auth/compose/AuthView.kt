package info.javaway.spend_sense.settings.child.auth.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import dev.icerock.moko.resources.compose.stringResource
import info.javaway.spend_sense.MR
import info.javaway.spend_sense.common.ui.atoms.AppButton
import info.javaway.spend_sense.common.ui.atoms.AppCard
import info.javaway.spend_sense.common.ui.theme.AppThemeProvider
import info.javaway.spend_sense.di.getKoinInstance
import info.javaway.spend_sense.settings.child.auth.child.register.compose.RegisterDialog
import info.javaway.spend_sense.settings.child.auth.child.signin.compose.SignInDialog

@Composable
fun AuthView(successListener: () -> Unit) {

    var showSignInDialog by remember { mutableStateOf(false) }
    var showRegisterDialog by remember { mutableStateOf(false) }

    AppCard {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                stringResource(MR.strings.to_sync_info),
                fontSize = 18.sp
            )

            AppButton(
                stringResource(MR.strings.enter),
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
            ) { showSignInDialog = true }

            Text(
                text = buildAnnotatedString {
                    append(stringResource(MR.strings.if_you_dont_have_acc))
                    withStyle(
                        style = SpanStyle(
                            color = AppThemeProvider.colors.accent,
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold
                        )
                    ) { append(stringResource(MR.strings.register)) }
                },
                modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                    .clickable { showRegisterDialog = true }
            )
        }
    }

    if (showSignInDialog) {
        Dialog(onDismissRequest = { showSignInDialog = false }) {
            SignInDialog(getKoinInstance()) {
                showSignInDialog = false
                successListener()
            }
        }
    }

    if (showRegisterDialog) {
        Dialog(onDismissRequest = { showRegisterDialog = false }) {
            RegisterDialog(getKoinInstance()) {
                showRegisterDialog = false
                successListener()
            }
        }
    }
}