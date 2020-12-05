package ru.shumilova.rick_and_morty.mvp.presenter.search_screen

import android.util.Log
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.shumilova.rick_and_morty.mvp.model.api.IDataSource
import ru.shumilova.rick_and_morty.mvp.view.search_screen.ISearchView
import ru.shumilova.rick_and_morty.mvp.view.search_screen.SearchType
import javax.inject.Inject

class SearchPresenter(
    private val mainThreadScheduler: Scheduler,
    private val searchType: SearchType
) : MvpPresenter<ISearchView>() {
    @Inject
    lateinit var api: IDataSource

    fun getData(page: Int) {
        api.getCharacters(page)
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.onGetResults(it.results ?: emptyList())
            },
                { Log.d("Error: ", "${it.message}") })
    }
}