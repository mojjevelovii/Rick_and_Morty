package ru.shumilova.rick_and_morty.mvp.model.domain

import io.reactivex.rxjava3.core.Single
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

interface ILocalRepository {
    fun getAllCharacters(): Single<List<CommonItem>>
    fun setFavoriteCharacter(character: CommonItem.Character)
    fun removeFavoriteCharacter(character: CommonItem.Character)
}