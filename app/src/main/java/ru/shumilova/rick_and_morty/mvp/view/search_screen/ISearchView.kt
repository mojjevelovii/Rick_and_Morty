package ru.shumilova.rick_and_morty.mvp.view.search_screen

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonResponse

@AddToEndSingle
interface ISearchView : MvpView {
    fun onGetResults(results: List<CommonResponse>)
}