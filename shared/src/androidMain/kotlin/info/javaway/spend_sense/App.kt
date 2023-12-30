package info.javaway.spend_sense

import android.app.Application
import android.content.Context
import info.javaway.spend_sense.di.initKoin
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(appModule = module {
            single<Context> { this@App.applicationContext }
        })
        instance = this
    }

    companion object{
        lateinit var instance: App
    }
}