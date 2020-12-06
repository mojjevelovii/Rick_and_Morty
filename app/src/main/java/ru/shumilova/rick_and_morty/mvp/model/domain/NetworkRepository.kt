package ru.shumilova.rick_and_morty.mvp.model.domain

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.shumilova.rick_and_morty.mvp.model.api.IDataSource
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Character
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Episode
import ru.shumilova.rick_and_morty.mvp.model.entity.api.Location
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

class NetworkRepository(private val api: IDataSource) : INetworkRepository {

    override fun getCharacters(page: Int): Single<List<CommonItem>> {
        return api.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .map {
                it.results?.mapNotNull { item ->
                    item?.let {
                        mapCharacter(it)
                    }
                } ?: emptyList()
            }
    }

    override fun getCharacter(id: Long): Single<CommonItem> {
        return api.getCharacter(id)
            .subscribeOn(Schedulers.io())
            .map {
                mapCharacter(it)
            }
    }

    override fun getLocations(page: Int): Single<List<CommonItem>> {
        return api.getLocations(page)
            .subscribeOn(Schedulers.io())
            .map {
                it.results?.mapNotNull { item ->
                    item?.let {
                        mapLocation(it)
                    }
                } ?: emptyList()
            }
    }

    override fun getLocation(id: Long): Single<CommonItem> {
        return api.getLocation(id)
            .subscribeOn(Schedulers.io())
            .map {
                mapLocation(it)
            }
    }

    override fun getEpisodes(page: Int): Single<List<CommonItem>> {
        return api.getEpisodes(page)
            .subscribeOn(Schedulers.io())
            .map {
                it.results?.mapNotNull { item ->
                    item?.let {
                        mapEpisode(it)
                    }
                } ?: emptyList()
            }
    }

    override fun getEpisode(id: Long): Single<CommonItem> {
        return api.getEpisode(id)
            .subscribeOn(Schedulers.io())
            .map {
                mapEpisode(it)
            }
    }

    private fun mapCharacter(item: Character) =
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
            created = item.created ?: ""
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