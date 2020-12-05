package ru.shumilova.rick_and_morty

import android.app.Application
import ru.shumilova.rick_and_morty.di.components.DaggerIAppComponent
import ru.shumilova.rick_and_morty.di.components.IAppComponent
import ru.shumilova.rick_and_morty.di.module.AppModule

class App : Application() {
    lateinit var appComponent: IAppComponent

    override fun onCreate() {
        super.onCreate()
        application = this

        appComponent = DaggerIAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        lateinit var application: App
    }
}