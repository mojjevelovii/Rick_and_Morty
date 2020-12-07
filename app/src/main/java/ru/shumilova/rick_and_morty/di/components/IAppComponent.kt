package ru.shumilova.rick_and_morty.di.components

import dagger.Component
import ru.shumilova.rick_and_morty.di.module.ApiModule
import ru.shumilova.rick_and_morty.di.module.AppModule
import ru.shumilova.rick_and_morty.di.module.RepoModule
import ru.shumilova.rick_and_morty.mvp.presenter.info_card.InfoCardPresenter
import ru.shumilova.rick_and_morty.mvp.presenter.search_screen.SearchPresenter
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)

interface IAppComponent {
    fun inject(searchPresenter: SearchPresenter)
    fun inject(infoCardPresenter: InfoCardPresenter)
}