package ru.shumilova.rick_and_morty.mvp.model.domain

import io.reactivex.rxjava3.core.Single
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

interface INetworkRepository {
    fun getCharacters(page: Int): Single<List<CommonItem>>
    fun getCharacter(id: Long): Single<CommonItem>
    fun findCharacters(name: String): Single<List<CommonItem>>

    fun getLocations(page: Int): Single<List<CommonItem>>
    fun getLocation(id: Long): Single<CommonItem>
    fun findLocations(name: String): Single<List<CommonItem>>

    fun getEpisodes(page: Int): Single<List<CommonItem>>
    fun getEpisode(id: Long): Single<CommonItem>
    fun findEpisodes(episode: String): Single<List<CommonItem>>
}