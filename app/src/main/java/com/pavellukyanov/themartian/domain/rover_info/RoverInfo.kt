package com.pavellukyanov.themartian.domain.rover_info

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class RoverInfo(
    val roverName: String,
    val landingDate: String,
    val launchData: String,
    val status: String,
    val maxSol: String,
    val maxDate: String,
    val totalPhotos: String
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "")
}