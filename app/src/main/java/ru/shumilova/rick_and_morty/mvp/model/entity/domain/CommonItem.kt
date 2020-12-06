package ru.shumilova.rick_and_morty.mvp.model.entity.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class CommonItem {
    @Parcelize
    data class Character(
        val id: Int,
        val name: String,
        val status: String,
        val species: String,
        val type: String,
        val gender: String,
        val origin: Origin,
        val location: Location,
        val image: String,
        val episode: List<String>,
        val url: String,
        val created: String
    ) : Parcelable, CommonItem() {

        @Parcelize
        data class Location(
            val name: String,
            val url: String
        ) : Parcelable

        @Parcelize
        data class Origin(
            val name: String,
            val url: String
        ) : Parcelable
    }

    @Parcelize
    data class Episode(
        val id: Int,
        val name: String,
        val airDate: String,
        val episode: String,
        val characters: List<String>,
        val url: String,
        val created: String
    ) : Parcelable, CommonItem()

    @Parcelize
    data class Location(
        val id: Int,
        val name: String,
        val type: String,
        val dimension: String,
        val residents: List<String>,
        val url: String,
        val created: String
    ) : Parcelable, CommonItem()
}