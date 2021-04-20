package com.pavellukyanov.themartian.data.database.models


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "rover_info")
class RoverInfoEntity(
    @PrimaryKey var roverName: String,
    val landingDate: String,
    val launchData: String,
    val status: String,
    var maxSol: String,
    var maxDate: String,
    var totalPhotos: String
) : Parcelable