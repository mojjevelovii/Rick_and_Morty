package ru.shumilova.rick_and_morty.mvp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.character_item.view.*
import kotlinx.android.synthetic.main.common_item.view.*
import ru.shumilova.rick_and_morty.R
import ru.shumilova.rick_and_morty.mvp.model.entity.domain.CommonItem

class ItemRVAdapter(private val listener: (CommonItem) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val CHARACTER = 1
        private const val LOCATION = 2
        private const val EPISODE = 3
    }

    // загрузка данных в адаптер
    var data: List<CommonItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    // метод определяет тип вью холдера
    override fun getItemViewType(position: Int): Int =
        when (data[position]) {
            is CommonItem.Character -> CHARACTER
            is CommonItem.Location -> LOCATION
            is CommonItem.Episode -> EPISODE
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CHARACTER -> CharactersViewHolder(
                inflater.inflate(
                    R.layout.character_item, parent, false
                )
            )

            LOCATION -> LocationsViewHolder(
                inflater.inflate(
                    R.layout.common_item, parent, false
                )
            )

            EPISODE -> EpisodesViewHolder(
                inflater.inflate(
                    R.layout.common_item, parent, false
                )
            )

            else -> throw IllegalArgumentException("Unknown view type!")
        }
    }

    // привязка данных из коллекции к вью холдеру
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CharactersViewHolder -> holder.bind(data[position] as CommonItem.Character)
            is LocationsViewHolder -> holder.bind(data[position] as CommonItem.Location)
            is EpisodesViewHolder -> holder.bind(data[position] as CommonItem.Episode)
        }
    }

    override fun getItemCount(): Int = data.size

    inner class CharactersViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(commonItem: CommonItem.Character) {
            containerView.tv_character_name_header.text = commonItem.name
            containerView.setOnClickListener { listener.invoke(commonItem) }

            Glide.with(containerView)
                .load(commonItem.image)
                .into(containerView.iv_character_item_image)
        }
    }

    inner class LocationsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(commonItem: CommonItem.Location) {
            containerView.tv_header.text = commonItem.name
            containerView.tv_body.text = commonItem.type
            containerView.setOnClickListener { listener.invoke(commonItem) }
        }
    }

    inner class EpisodesViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(commonItem: CommonItem.Episode) {
            containerView.tv_header.text = commonItem.episode
            containerView.tv_body.text = commonItem.name
            containerView.setOnClickListener { listener.invoke(commonItem) }
        }
    }
}