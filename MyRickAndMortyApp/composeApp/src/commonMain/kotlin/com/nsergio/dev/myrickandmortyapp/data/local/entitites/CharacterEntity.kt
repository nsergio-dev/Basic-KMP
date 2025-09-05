package com.nsergio.dev.myrickandmortyapp.data.local.entitites

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "characters")
data class CharacterEntity(

    @PrimaryKey
    val id: Int,
    val status: String,
    val name: String,
    val gender: String,
    val image: String
)
