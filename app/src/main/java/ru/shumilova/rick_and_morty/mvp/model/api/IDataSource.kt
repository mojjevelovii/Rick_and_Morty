package ru.shumilova.rick_and_morty.mvp.model.api

import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.shumilova.rick_and_morty.mvp.model.entity.api.*

interface IDataSource {
    @GET("character")
    fun getCharacters(@Query("page") page: Int): Single<CharacterResponse>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Long): Single<Character>

    @GET("location")
    fun getLocations(@Query("page") page: Int): Single<LocationsResponse>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Long): Single<Location>

    @GET("episode")
    fun getEpisodes(@Query("page") page: Int): Single<EpisodesResponse>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Long): Single<Episode>
}