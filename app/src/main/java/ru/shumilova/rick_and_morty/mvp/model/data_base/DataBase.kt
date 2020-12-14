package ru.shumilova.rick_and_morty.mvp.model.data_base

import androidx.room.RoomDatabase
import ru.shumilova.rick_and_morty.mvp.model.data_base.dao.ICharacterDao
import ru.shumilova.rick_and_morty.mvp.model.data_base.entity.CharacterEntity

@androidx.room.Database(
    entities = [CharacterEntity::class], version = 1
)

abstract class DataBase : RoomDatabase() {
    abstract val characterDao: ICharacterDao

    companion object {
        const val DB_NAME = "favorite_characters.db"
    }
}
