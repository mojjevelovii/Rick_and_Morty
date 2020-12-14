package ru.shumilova.rick_and_morty.mvp.model.domain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.rick_and_morty.mvp.model.data_base.DataBase
import ru.shumilova.rick_and_morty.mvp.model.data_base.entity.CharacterEntity
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

class LocalRepository(private val favoriteDB: DataBase, private val gson: Gson) : ILocalRepository {

    override fun getAllCharacters(): Single<List<CommonItem>> {
        return favoriteDB
            .characterDao
            .getAll()
            .subscribeOn(Schedulers.io())
            .map {
                it.map { characterEntity ->
                    CommonItem.Character(
                        id = characterEntity.id,
                        name = characterEntity.name,
                        status = characterEntity.status,
                        species = characterEntity.species,
                        type = characterEntity.type,
                        gender = characterEntity.gender,
                        origin = gson.fromJson(
                            characterEntity.origin,
                            CommonItem.Character.Origin::class.java
                        ),
                        location = gson.fromJson(
                            characterEntity.location,
                            CommonItem.Character.Location::class.java
                        ),
                        image = characterEntity.image,
                        episode = gson.fromJson(
                            characterEntity.episode,
                            object : TypeToken<List<String>>() {}.type
                        ),
                        url = characterEntity.url,
                        created = characterEntity.created,
                        isFavorite = true
                    )
                }
            }
    }

    override fun setFavoriteCharacter(character: CommonItem.Character) {
        val convertedCharacter = mapToEntity(character)
        favoriteDB
            .characterDao
            .insert(convertedCharacter)

    }

    private fun mapToEntity(character: CommonItem.Character): CharacterEntity {
        return CharacterEntity(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            origin = gson.toJson(character.origin),
            location = gson.toJson(character.location),
            image = character.image,
            episode = gson.toJson(character.episode),
            url = character.url,
            created = character.created
        )
    }

    override fun removeFavoriteCharacter(character: CommonItem.Character) {
        val removeCharacter = mapToEntity(character)
        favoriteDB.characterDao.delete(removeCharacter)
    }
}