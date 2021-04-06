package com.pavellukyanov.themartian.data.database.models


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rover_info")
class RoverInfoEntity(
    @PrimaryKey var roverName: String,
    val landingDate: String,
    val launchData: String,
    val status: String,
    var maxSol: String,
    var maxDate: String,
    var totalPhotos: String
)