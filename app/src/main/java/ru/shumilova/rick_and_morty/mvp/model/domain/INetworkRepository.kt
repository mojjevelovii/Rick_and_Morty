package ru.shumilova.rick_and_morty.mvp.model.domain

import io.reactivex.rxjava3.core.Single
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonResponse

interface INetworkRepository {
    fun getCharacters(page: Int): Single<List<CommonResponse>>
    fun getCharacter(id: Long): Single<CommonResponse>

    fun getLocations(page: Int): Single<List<CommonResponse>>
    fun getLocation(id: Long): Single<CommonResponse>

    fun getEpisodes(page: Int): Single<List<CommonResponse>>
    fun getEpisode(id: Long): Single<CommonResponse>
}