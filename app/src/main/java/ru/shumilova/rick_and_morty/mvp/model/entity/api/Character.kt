package ru.shumilova.rick_and_morty.mvp.model.entity.api

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("name")
    @Expose
    val name: String? = null,

    @SerializedName("status")
    @Expose
    val status: String? = null,

    @SerializedName("species")
    @Expose
    val species: String? = null,

    @SerializedName("type")
    @Expose
    val type: String? = null,

    @SerializedName("gender")
    @Expose
    val gender: String? = null,

    @SerializedName("origin")
    @Expose
    val origin: Origin? = null,

    @SerializedName("location")
    @Expose
    val location: Location? = null,

    @SerializedName("image")
    @Expose
    val image: String? = null,

    @SerializedName("episode")
    @Expose
    val episode: List<String?>? = null,

    @SerializedName("url")
    @Expose
    val url: String? = null,

    @SerializedName("created")
    @Expose
    val created: String? = null
) : Parcelable {


    @Parcelize
    data class Location(
        @SerializedName("name")
        @Expose
        val name: String? = null,

        @SerializedName("url")
        @Expose
        val url: String? = null
    ) : Parcelable

    @Parcelize
    data class Origin(
        @SerializedName("name")
        @Expose
        val name: String? = null,

        @SerializedName("url")
        @Expose
        val url: String? = null
    ) : Parcelable
}