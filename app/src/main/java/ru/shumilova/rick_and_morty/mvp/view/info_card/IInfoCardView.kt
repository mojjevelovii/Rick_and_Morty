package ru.shumilova.rick_and_morty.mvp.view.info_card

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

@AddToEndSingle
interface IInfoCardView : MvpView {
    fun onGetResults(results: List<CommonItem>)
}