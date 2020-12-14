package ru.shumilova.rick_and_morty.di.module

import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.shumilova.rick_and_morty.App
import ru.shumilova.rick_and_morty.mvp.model.data_base.DataBase
import ru.shumilova.rick_and_morty.mvp.model.domain.ILocalRepository
import ru.shumilova.rick_and_morty.mvp.model.domain.LocalRepository
import javax.inject.Singleton

@Module
class FavoritesModule {
    @Singleton
    @Provides
    fun favoriteDB(app: App): DataBase =
        Room.databaseBuilder(app, DataBase::class.java, DataBase.DB_NAME).build()

    @Singleton
    @Provides
    fun favoriteRepository(favoriteDB: DataBase): ILocalRepository =
        LocalRepository(favoriteDB, Gson())
}