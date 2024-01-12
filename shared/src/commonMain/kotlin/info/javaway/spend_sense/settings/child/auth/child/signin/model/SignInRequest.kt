package info.javaway.spend_sense.settings.child.auth.child.signin.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val identifier: String,
    val password: String,
)
