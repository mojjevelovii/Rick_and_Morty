package ru.shumilova.rick_and_morty.mvp.model.domain

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.rick_and_morty.mvp.model.api.IDataSource
import ru.shumilova.rick_and_morty.mvp.model.data_base.DataBase
import ru.shumilova.rick_and_morty.mvp.model.data_base.entity.CharacterEntity
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Character
import ru.shumilova.rick_and_morty.mvp.model.entity.api.CharacterResponse
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Episode
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Location
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

class NetworkRepository(
    private val api: IDataSource,
    private val favoriteDB: DataBase
) : INetworkRepository {

    override fun getCharacters(page: Int): Single<List<CommonItem>> {
        return api.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .zipWith(
                favoriteDB.characterDao.getAll().subscribeOn(Schedulers.io()),
                BiFunction<CharacterResponse, List<CharacterEntity>, List<CommonItem>> { netCharacters, favorites ->
                    mapResponseList(netCharacters.results, favorites)
                }
            )
    }

    override fun getCharacters(ids: String): Single<List<CommonItem>> {
        return api.getCharacters(ids)
            .subscribeOn(Schedulers.io())
            .zipWith(
                favoriteDB.characterDao.getAll().subscribeOn(Schedulers.io()),
                BiFunction<List<Character>, List<CharacterEntity>, List<CommonItem>> { netCharacters, favorites ->
                    netCharacters.map { mapCharacter(it, favorites) }
                }
            )
    }

    override fun getCharacter(id: Long): Single<CommonItem> {
        return api.getCharacter(id)
            .subscribeOn(Schedulers.io())
            .zipWith(
                favoriteDB.characterDao.getAll().subscribeOn(Schedulers.io()),
                BiFunction<Character, List<CharacterEntity>, CommonItem> { netCharacter, favorites ->
                    mapCharacter(netCharacter, favorites)
                })
    }

    override fun findCharacters(name: String): Single<List<CommonItem>> {
        return api.findCharacter(name)
            .subscribeOn(Schedulers.io())
            .zipWith(
                favoriteDB.characterDao.getAll().subscribeOn(Schedulers.io()),
                BiFunction<CharacterResponse, List<CharacterEntity>, List<CommonItem>> { netCharacters, favorites ->
                    mapResponseList(netCharacters.results, favorites)
                }
            )
    }

    override fun getLocations(page: Int): Single<List<CommonItem>> {
        return api.getLocations(page)
            .subscribeOn(Schedulers.io())
            .map { mapResponseList(it.results) }
    }

    override fun getLocation(id: Long): Single<CommonItem> {
        return api.getLocation(id)
            .subscribeOn(Schedulers.io())
            .map {
                mapLocation(it)
            }
    }

    override fun findLocations(name: String): Single<List<CommonItem>> {
        return api.findLocation(name)
            .map { mapResponseList(it.results) }
    }

    override fun getEpisodes(page: Int): Single<List<CommonItem>> {
        return api.getEpisodes(page)
            .subscribeOn(Schedulers.io())
            .map { mapResponseList(it.results) }
    }

    override fun getEpisodes(ids: String): Single<List<CommonItem>> {
        return api.getEpisodes(ids)
            .subscribeOn(Schedulers.io())
            .map { it.map { mapEpisode(it) } }
    }

    override fun getEpisode(id: Long): Single<CommonItem> {
        return api.getEpisode(id)
            .subscribeOn(Schedulers.io())
            .map { mapEpisode(it) }
    }

    override fun findEpisodes(episode: String): Single<List<CommonItem>> {
        return api.findEpisode(episode)
            .subscribeOn(Schedulers.io())
            .map { mapResponseList(it.results) }
    }

    private fun <T> mapResponseList(list: List<T?>?, favorites: List<CharacterEntity> = emptyList()): List<CommonItem> {
        return list?.mapNotNull { item ->
            item?.let {
                when (it) {
                    is Character -> mapCharacter(it, favorites)
                    is Episode -> mapEpisode(it)
                    is Location -> mapLocation(it)

                    else -> null
                }
            }
        } ?: emptyList()
    }

    private fun mapCharacter(item: Character, favorites: List<CharacterEntity>) =
        CommonItem.Character(
            id = item.id ?: -1,
            name = item.name ?: "",
            status = item.status ?: "",
            species = item.species ?: "",
            type = item.type ?: "",
            gender = item.gender ?: "",
            origin = CommonItem.Character.Origin(
                name = item.origin?.name ?: "",
                url = item.origin?.url ?: ""
            ),
            location = CommonItem.Character.Location(
                name = item.location?.name ?: "",
                url = item.location?.url ?: ""
            ),
            image = item.image ?: "",
            episode = item.episode?.filterNotNull() ?: emptyList(),
            url = item.url ?: "",
            created = item.created ?: "",
            isFavorite = favorites.find { it.id == item.id } != null
        )

    private fun mapLocation(item: Location) =
        CommonItem.Location(
            id = item.id ?: -1,
            name = item.name ?: "",
            type = item.type ?: "",
            dimension = item.dimension ?: "",
            residents = item.residents?.filterNotNull() ?: emptyList(),
            url = item.url ?: "",
            created = item.created ?: ""
        )

    private fun mapEpisode(item: Episode) =
        CommonItem.Episode(
            id = item.id ?: -1,
            name = item.name ?: "",
            airDate = item.airDate ?: "",
            episode = item.episode ?: "",
            characters = item.characters?.filterNotNull() ?: emptyList(),
            url = item.url ?: "",
            created = item.created ?: ""
        )
}