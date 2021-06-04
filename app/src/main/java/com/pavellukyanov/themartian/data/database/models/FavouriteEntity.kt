package com.pavellukyanov.themartian.data.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourites_photo")
class FavouriteEntity(
    @PrimaryKey
    var id: Long,
    val sol: Long,
    val srcPhoto: String,
    val dataEarth: String,
    val rover: String,
    val camera: String
)