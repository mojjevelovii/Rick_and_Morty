package ru.shumilova.rick_and_morty.mvp.presenter.search_screen

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
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
    private val compositeDisposable = CompositeDisposable()

    fun getData() {
        if (getDataNotStarted) {
            getDataNotStarted = false // флаг для отметки доступности следующего запроса
            val disposable = getTypedRequest(searchType, ++currentPage)
                .observeOn(mainThreadScheduler)
                .subscribe({
                    pages.addAll(it)
                    viewState.onGetResults(pages)
                    getDataNotStarted = true
                },
                    {
                        it.printStackTrace()
                        getDataNotStarted = true
                    })
            compositeDisposable.add(disposable)
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

    fun findBy(text: String) {
        compositeDisposable.clear()
        val disposable = getTypedSearch(searchType, text)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.onGetResults(it)
            },
                { Log.d("Error: ", "${it.message}") })
        compositeDisposable.add(disposable)
    }

    private fun getTypedSearch(searchType: SearchType, text: String)
            : Single<List<CommonItem>> {
        return when (searchType) {
            SearchType.CHARACTERS -> repository.findCharacters(text)
            SearchType.LOCATIONS -> repository.findLocations(text)
            SearchType.EPISODES -> repository.findEpisodes(text)
        }
    }

    fun clearSearch() {
        viewState.onGetResults(pages)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}