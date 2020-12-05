package ru.shumilova.rick_and_morty.di.module

import dagger.Module
import dagger.Provides
import ru.shumilova.rick_and_morty.mvp.model.api.IDataSource
import ru.shumilova.rick_and_morty.mvp.model.domain.INetworkRepository
import ru.shumilova.rick_and_morty.mvp.model.domain.NetworkRepository
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun networkRepo(api: IDataSource): INetworkRepository = NetworkRepository(api)
}