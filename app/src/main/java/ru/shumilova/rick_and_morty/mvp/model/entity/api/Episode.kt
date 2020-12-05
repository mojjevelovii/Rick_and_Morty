package ru.shumilova.rick_and_morty.mvp.model.entity.api

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Episode(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("air_date")
    @Expose
    val airDate: String? = null,

    @SerializedName("episode")
    @Expose
    val episode: String? = null,

    @SerializedName("characters")
    @Expose
    val characters: List<String?>? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("created")
    @Expose
    val created: String? = null
) : Parcelable