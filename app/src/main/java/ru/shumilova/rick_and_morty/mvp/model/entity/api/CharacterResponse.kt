package ru.shumilova.rick_and_morty.mvp.model.entity.api

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterResponse(
    @SerializedName("info")
    @Expose
    val info: InfoResponse? = null,

    @SerializedName("results")
    @Expose
    val results: List<Character?>? = null
) : Parcelable