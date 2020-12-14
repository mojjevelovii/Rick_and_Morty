package ru.shumilova.rick_and_morty.mvp.model.data_base.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_characters"
)

data class CharacterEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val status: String = "",
    val species: String = "",
    val type: String = "",
    val gender: String = "",
    val origin: String = "",
    val location: String = "",
    val image: String = "",
    val episode: String = "",
    val url: String = "",
    val created: String = ""
)