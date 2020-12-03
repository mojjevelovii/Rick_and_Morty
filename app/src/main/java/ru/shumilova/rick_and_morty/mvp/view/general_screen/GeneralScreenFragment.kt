package ru.shumilova.rick_and_morty.mvp.view.general_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_general_screen.*
import ru.shumilova.rick_and_morty.R

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
            it.findNavController().navigate(R.id.action_general_screen_to_charactersFragment)
        }

        btn_locations.setOnClickListener {
            it.findNavController().navigate(R.id.action_general_screen_to_locationsFragment)
        }

        btn_episodes.setOnClickListener {
            it.findNavController().navigate(R.id.action_general_screen_to_episodesFragment)
        }
    }

}