package ru.shumilova.rick_and_morty.mvp.view.info_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.character_item.tv_character_name_header
import kotlinx.android.synthetic.main.fragment_info_card.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.shumilova.rick_and_morty.App
import ru.shumilova.rick_and_morty.R
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem
import ru.shumilova.rick_and_morty.mvp.presenter.info_card.InfoCardPresenter
import ru.shumilova.rick_and_morty.mvp.view.adapter.ItemRVAdapter
import ru.shumilova.rick_and_morty.mvp.view.search_screen.SearchType

class InfoCardFragment : MvpAppCompatFragment(), IInfoCardView {
    private val args: InfoCardFragmentArgs by navArgs()
    private var adapter: ItemRVAdapter? = null

    private val presenter: InfoCardPresenter by moxyPresenter {
        InfoCardPresenter(AndroidSchedulers.mainThread(), getItemType(args.item))
            .apply { App.application.appComponent.inject(this) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_info_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when (val item = args.item) {
            is CommonItem.Character -> bindItem(item)
            is CommonItem.Episode -> bindItem(item)
            is CommonItem.Location -> bindItem(item)
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = ItemRVAdapter { item ->
            val action = InfoCardFragmentDirections.infoCardSelf(item)
            findNavController().navigate(action)
        }

        rv_episodes_container.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        rv_episodes_container.adapter = adapter
    }

    private fun bindItem(item: CommonItem.Character) {
        tv_character_name_header.text = item.name
        Glide.with(this).load(item.image).into(iv_character_image)
        tv_character_status.text = item.status
        tv_character_species.text = item.species
        tv_character_gender.text = item.gender
        tv_character_location.text = item.location.name
        tv_character_origin.text = item.origin.name
        presenter.getEpisodes(item.episode)
    }

    private fun bindItem(item: CommonItem.Location) {
        tv_character_name_header.text = item.name
        ll_character_image_container.isVisible = false
        tv_status_head.setText(R.string.type)
        tv_character_status.text = item.type
        tv_character_species.text = item.dimension
        tv_species.setText(R.string.dimension)
        ll_character_gender_container.isVisible = false
        tv_episodes.isVisible = false
        ll_character_origin_container.isVisible = false
        ll_character_location_container.isVisible = false
        presenter.getCharacters(item.residents)
    }

    private fun bindItem(item: CommonItem.Episode) {
        tv_character_name_header.text = item.name
        ll_character_image_container.isVisible = false
        tv_status_head.setText(R.string.date_aired)
        tv_character_status.text = item.airDate
        tv_character_species.text = item.episode
        tv_species.setText(R.string.episode)
        ll_character_gender_container.isVisible = false
        tv_episodes.isVisible = false
        ll_character_origin_container.isVisible = false
        ll_character_location_container.isVisible = false
        presenter.getCharacters(item.characters)
    }

    private fun getItemType(item: CommonItem): SearchType {
        return when (item) {
            is CommonItem.Character -> SearchType.CHARACTERS
            is CommonItem.Episode -> SearchType.EPISODES
            is CommonItem.Location -> SearchType.LOCATIONS
        }
    }

    override fun onGetResults(results: List<CommonItem>) {
        adapter?.data = results
    }
}