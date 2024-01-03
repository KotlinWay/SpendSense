package info.javaway.spend_sense.di

import info.javaway.spend_sense.categories.CategoriesRepository
import info.javaway.spend_sense.categories.list.CategoriesViewModel
import info.javaway.spend_sense.common.ui.calendar.DatePickerViewModel
import info.javaway.spend_sense.platform.DeviceInfo
import info.javaway.spend_sense.root.RootViewModel
import info.javaway.spend_sense.settings.SettingsViewModel
import info.javaway.spend_sense.storage.SettingsManager
import org.koin.dsl.module

object CoreModule {
    val deviceInfo = module  {
        single { DeviceInfo() }
    }
}

object StorageModule {
    val settings = module {
        single { SettingsManager(get()) }
    }
}

object RepositoriesModule {
    val repositories = module {
        single { CategoriesRepository() }
    }
}

object ViewModelsModule{
    val viewModels = module {
        single { RootViewModel(get()) }
        factory { SettingsViewModel(get(), get()) }
        single { DatePickerViewModel() }
        single { CategoriesViewModel(get()) }
    }
}