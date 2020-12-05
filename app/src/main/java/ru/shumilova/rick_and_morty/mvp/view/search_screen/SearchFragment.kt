package ru.shumilova.rick_and_morty.mvp.view.search_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_search.*
import moxy.MvpAppCompatFragment
import ru.shumilova.rick_and_morty.R

class SearchFragment : MvpAppCompatFragment(), ISearchView {
    val args: SearchFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (args.searchType) {
            SearchType.CHARACTERS -> setTitle(R.string.characters)
            SearchType.LOCATIONS -> setTitle(R.string.locations)
            SearchType.EPISODES -> setTitle(R.string.episodes)
        }
    }

    private fun setTitle(header: Int) {
        tv_header.setText(header)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {

            }
    }
}