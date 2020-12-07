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

    @GET("character/{id}")
    fun getCharacters(@Path("id") id: String): Single<List<Character>>

    @GET("character")
    fun findCharacter(@Query("name") name: String): Single<CharacterResponse>

    @GET("location")
    fun getLocations(@Query("page") page: Int): Single<LocationsResponse>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Long): Single<Location>

    @GET("location")
    fun findLocation(@Query("name") name: String): Single<LocationsResponse>

    @GET("episode")
    fun getEpisodes(@Query("page") page: Int): Single<EpisodesResponse>

    @GET("episode/{id}")
    fun getEpisodes(@Path("id") id: String): Single<List<Episode>>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Long): Single<Episode>

    @GET("episode")
    fun findEpisode(@Query("episode") episode: String): Single<EpisodesResponse>
}