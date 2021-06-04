package com.pavellukyanov.themartian.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

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
    constructor(): this("", "", "", "", "", "", "")
}