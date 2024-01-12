package info.javaway.spend_sense.network

import info.javaway.spend_sense.categories.model.CategoryApi
import info.javaway.spend_sense.events.model.SpendEventApi
import info.javaway.spend_sense.settings.child.auth.child.register.model.RegisterRequest
import info.javaway.spend_sense.settings.child.auth.child.signin.model.SignInRequest
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AppApi(
    private val httpClient: HttpClient,
    private val settings: SettingsManager
) {

    suspend fun register(req: RegisterRequest) =
        httpClient.post("${settings.serverUrl}/api/auth/local/register") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun signIn(req: SignInRequest) =
        httpClient.post("${settings.serverUrl}/api/auth/local") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun syncCategories(categories: List<CategoryApi>) =
        httpClient.post("${settings.serverUrl}/api/categories/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(categories)
        }

    suspend fun syncEvents(events: List<SpendEventApi>) =
        httpClient.post("${settings.serverUrl}/api/spend-events/sync") {
            contentType(ContentType.Application.Json)
            bearerAuth(settings.token)
            setBody(events)
        }
}