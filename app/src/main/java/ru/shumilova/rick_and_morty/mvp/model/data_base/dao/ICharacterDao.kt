package ru.shumilova.rick_and_morty.mvp.model.data_base.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.rxjava3.core.Single
import ru.shumilova.rick_and_morty.mvp.model.data_base.entity.CharacterEntity

@Dao
interface ICharacterDao : IBaseDao<CharacterEntity> {
    @Query("SELECT * FROM favorite_characters")
    fun getAll(): Single<List<CharacterEntity>>
}