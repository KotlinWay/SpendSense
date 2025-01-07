@file:OptIn(ExperimentalSerializationApi::class)

package info.javaway.spend_sense.di

import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.list.CategoriesComponent
import info.javaway.spend_sense.categories.model.CategoryDao
import info.javaway.spend_sense.db.AppDb
import info.javaway.spend_sense.events.EventsRepository
import info.javaway.spend_sense.events.create.CreateEventComponent
import info.javaway.spend_sense.events.list.EventsComponent
import info.javaway.spend_sense.events.model.SpendEventDao
import info.javaway.spend_sense.extensions.appLog
import info.javaway.spend_sense.network.AppApi
import info.javaway.spend_sense.network.DateSerializer
import info.javaway.spend_sense.network.DateTimeSerializer
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.root.RootComponent
import info.javaway.spend_sense.settings.SettingsComponent
import info.javaway.spend_sense.settings.child.auth.AuthComponent
import info.javaway.spend_sense.settings.child.auth.child.register.RegisterComponent
import info.javaway.spend_sense.settings.child.auth.child.signin.SignInComponent
import info.javaway.spend_sense.settings.child.sync.compose.SyncComponent
import info.javaway.spend_sense.storage.DbAdapters
import info.javaway.spend_sense.storage.SettingsManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object CoreModule {
    val deviceInfo = module {
        single { DeviceInfo() }
        factory { Dispatchers.Default + SupervisorJob() }
    }
}

object NetworkModule {
    val json = module {
        single {
            Json {
                encodeDefaults = false
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = true
                serializersModule = SerializersModule {
                    contextual(LocalDateTime::class, DateTimeSerializer)
                    contextual(LocalDate::class, DateSerializer)
                }
            }
        }
    }

    val httpClient = module {
        single {
            HttpClient(CIO) {
                expectSuccess = false
                install(ContentNegotiation) {
                    json(get())
                }
                install(Logging) {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            appLog(message)
                        }
                    }
                }
            }
        }
    }

    val api = module { single { AppApi(get(), get()) } }
}

object StorageModule {
    val settings = module {
        single { SettingsManager(get()) }
    }
    val db = module {
        single {
            AppDb(get(), DbAdapters.categoryDbAdapter, DbAdapters.eventDbAdapter)
        }
    }
    val dao = module {
        single { CategoryDao(get(), get()) }
        single { SpendEventDao(get(), get()) }
    }
}

object RepositoriesModule {
    val repositories = module {
        single { CategoriesRepository(get()) }
        single { EventsRepository(get()) }
    }
}

object ViewModelsModule {
    val components = module {
        singleOf(RootComponent::Factory)
        singleOf(SettingsComponent::Factory)
        factoryOf(EventsComponent::Factory)
        factoryOf(CategoriesComponent::Factory)
        factoryOf(CreateEventComponent::Factory)
        factoryOf(RegisterComponent::Factory)
        factoryOf(SignInComponent::Factory)
        factoryOf(AuthComponent::Factory)
        factoryOf(SyncComponent::Factory)
    }
}
