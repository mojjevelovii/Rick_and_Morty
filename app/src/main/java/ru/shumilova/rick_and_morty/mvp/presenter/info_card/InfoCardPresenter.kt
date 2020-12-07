package ru.shumilova.rick_and_morty.mvp.presenter.info_card

import androidx.core.net.toUri
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import moxy.MvpPresenter
import ru.shumilova.rick_and_morty.mvp.model.domain.INetworkRepository
import ru.shumilova.rick_and_morty.mvp.view.info_card.IInfoCardView
import ru.shumilova.rick_and_morty.mvp.view.search_screen.SearchType
import javax.inject.Inject

class InfoCardPresenter(
    private val mainThreadScheduler: Scheduler,
    private val searchType: SearchType
) : MvpPresenter<IInfoCardView>() {

    @Inject
    lateinit var repository: INetworkRepository
    private val compositeDisposable = CompositeDisposable()

    fun getEpisodes(episodes: List<String>) {
        val requestedEpisodes = episodes
            .map { it.toUri().lastPathSegment }
            .joinToString(",")

        val disposable = repository.getEpisodes(requestedEpisodes)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.onGetResults(it)

            },
                {
                    it.printStackTrace()
                })
        compositeDisposable.add(disposable)
    }

    fun getCharacters(characters: List<String>) {
        val requestedCharacters = characters
            .map { it.toUri().lastPathSegment }
            .joinToString(",")

        val disposable = repository.getCharacters(requestedCharacters)
            .observeOn(mainThreadScheduler)
            .subscribe({
                viewState.onGetResults(it)

            },
                {
                    it.printStackTrace()
                })
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}