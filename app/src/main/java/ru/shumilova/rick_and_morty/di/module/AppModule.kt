package ru.shumilova.rick_and_morty.di.module

import dagger.Module
import dagger.Provides
import ru.shumilova.rick_and_morty.App

@Module
class AppModule(private val app: App) {
    @Provides
    fun app(): App = app
}