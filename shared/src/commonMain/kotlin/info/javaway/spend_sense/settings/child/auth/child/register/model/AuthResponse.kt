package info.javaway.spend_sense.settings.child.auth.child.register.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val jwt: String?
)
