package ru.shumilova.rick_and_morty.mvp.view.search_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        iv_search.setOnClickListener {
            et_search.text?.clear()
        }

        // запросы на поиск по введенному тексту по мере его ввода
        et_search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    setSearchIcon(it)
                    if (it.length >= 2)
                        presenter.findBy(it.toString())
                    else if (it.isEmpty())
                        presenter.clearSearch()
                } ?: presenter.clearSearch()
            }
        })
    }

    private fun setSearchIcon(text: CharSequence?) {
        if (text.isNullOrEmpty()) {
            iv_search.setImageResource(R.drawable.ic_search_24)
            iv_search.isClickable = false
        } else {
            iv_search.setImageResource(R.drawable.ic_clear_24)
            iv_search.isClickable = true
        }
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
            if (isNeedNextPage(lastVisiblePosition))
                presenter.getData()
        }
    }

    private fun isNeedNextPage(lastVisiblePosition: Int) = et_search.text.isNullOrEmpty() &&
            lastVisiblePosition >=
            (adapter?.data?.size ?: 0) - 10

    // сэтим данные в rv
    override fun onGetResults(results: List<CommonItem>) {
        adapter?.data = results
    }
}