package ru.shumilova.rick_and_morty.mvp.view.general_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_general_screen.*
import ru.shumilova.rick_and_morty.R
import ru.shumilova.rick_and_morty.mvp.view.search_screen.SearchType

class GeneralScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_general_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(view)
            .load(R.drawable.rm)
            .into(iv_main_picture)

        btn_characters.setOnClickListener {
            navigateTo(SearchType.CHARACTERS)
        }

        btn_locations.setOnClickListener {
            navigateTo(SearchType.LOCATIONS)
        }

        btn_episodes.setOnClickListener {
            navigateTo(SearchType.EPISODES)
        }
    }

    private fun navigateTo(searchType: SearchType) {
        val action = GeneralScreenFragmentDirections.actionToSearch(searchType)
        findNavController().navigate(action)
    }

}