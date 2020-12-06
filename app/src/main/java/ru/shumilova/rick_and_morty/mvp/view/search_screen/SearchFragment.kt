package ru.shumilova.rick_and_morty.mvp.view.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_search.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.shumilova.rick_and_morty.App
import ru.shumilova.rick_and_morty.R
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem
import ru.shumilova.rick_and_morty.mvp.presenter.search_screen.SearchPresenter
import ru.shumilova.rick_and_morty.mvp.view.adapter.ItemRVAdapter

class SearchFragment : MvpAppCompatFragment(), ISearchView {
    private val args: SearchFragmentArgs by navArgs()
    private var adapter: ItemRVAdapter? = null

    private val presenter: SearchPresenter by moxyPresenter {
        SearchPresenter(AndroidSchedulers.mainThread(), args.searchType)
            .apply { App.application.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (args.searchType) {
            SearchType.CHARACTERS -> setTitle(R.string.characters)
            SearchType.LOCATIONS -> setTitle(R.string.locations)
            SearchType.EPISODES -> setTitle(R.string.episodes)
        }

        initRecyclerView()
        presenter.getData(1)
    }

    private fun setTitle(header: Int) {
        tv_header.setText(header)
    }

    private fun initRecyclerView() {
        adapter = ItemRVAdapter { item -> }
        rv_characters.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_characters.adapter = adapter

    }

    // сетим данные в rv
    override fun onGetResults(results: List<CommonItem>) {
        adapter?.data = results
    }
}