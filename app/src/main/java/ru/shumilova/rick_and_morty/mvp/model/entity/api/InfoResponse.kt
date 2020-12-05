package ru.shumilova.rick_and_morty.mvp.model.entity.api

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InfoResponse(
    @SerializedName("count")
    @Expose
    val count: Int? = null,

    @SerializedName("pages")
    @Expose
    val pages: Int? = null,

    @SerializedName("next")
    @Expose
    val next: String? = null,

    @SerializedName("prev")
    @Expose
    val prev: String? = null
) : Parcelable