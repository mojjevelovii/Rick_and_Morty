package ru.shumilova.rick_and_morty.mvp.view.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
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
        presenter.getData()
    }

    private fun setTitle(header: Int) {
        tv_header.setText(header)
    }

    private fun initRecyclerView() {
        adapter = ItemRVAdapter { item -> }
        rv_characters.layoutManager = GridLayoutManager(requireContext(), 2)
        rv_characters.adapter = adapter

        // вычисление момента запуска зарузки следующей страницы
        rv_characters.setOnScrollChangeListener { _, _, _, _, _ ->
            val lastVisiblePosition = (rv_characters.layoutManager as GridLayoutManager)
                .findLastVisibleItemPosition()
            if (lastVisiblePosition >= (adapter?.data?.size ?: 0) - 10)
                presenter.getData()
        }
    }

    // сэтим данные в rv
    override fun onGetResults(results: List<CommonItem>) {
        adapter?.data = results
    }
}