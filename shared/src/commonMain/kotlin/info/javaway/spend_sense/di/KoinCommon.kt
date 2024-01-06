package info.javaway.spend_sense.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier
import org.koin.dsl.module

expect val platformModule: Module

inline fun <reified T> getKoinInstance(qualifier: Qualifier? = null) : T {
    return object : KoinComponent {
        val value : T by inject(qualifier)
    }.value
}

fun initKoin(
    appModule : Module = module {  }
) = startKoin {
    modules(
        CoreModule.deviceInfo,
        StorageModule.settings,
        platformModule,
        appModule,
        ViewModelsModule.viewModels,
        RepositoriesModule.repositories,
        StorageModule.db,
        StorageModule.dao
    )
}