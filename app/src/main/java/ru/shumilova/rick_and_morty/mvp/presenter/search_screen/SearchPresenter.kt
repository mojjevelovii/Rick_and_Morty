package ru.shumilova.rick_and_morty.mvp.presenter.search_screen

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.shumilova.rick_and_morty.mvp.model.domain.INetworkRepository
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem
import ru.shumilova.rick_and_morty.mvp.view.search_screen.ISearchView
import ru.shumilova.rick_and_morty.mvp.view.search_screen.SearchType
import javax.inject.Inject

class SearchPresenter(
    private val mainThreadScheduler: Scheduler,
    private val searchType: SearchType
) : MvpPresenter<ISearchView>() {
    @Inject
    lateinit var repository: INetworkRepository

    private var currentPage = 0
    private val pages: MutableList<CommonItem> = ArrayList()
    private var getDataNotStarted: Boolean = true

    fun getData() {
        if (getDataNotStarted) {
            getDataNotStarted = false // флаг для отметки доступности следующего запроса
            getTypedRequest(searchType, ++currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .subscribe({
                    pages.addAll(it)
                    viewState.onGetResults(pages)
                    getDataNotStarted = true
                },
                    {
                        Log.d("Error: ", "${it.message}")
                        getDataNotStarted = true
                    })
        }

    }

    private fun getTypedRequest(searchType: SearchType, page: Int)
            : Single<List<CommonItem>> {
        return when (searchType) {
            SearchType.CHARACTERS -> repository.getCharacters(page)
            SearchType.LOCATIONS -> repository.getLocations(page)
            SearchType.EPISODES -> repository.getEpisodes(page)
        }
    }
}