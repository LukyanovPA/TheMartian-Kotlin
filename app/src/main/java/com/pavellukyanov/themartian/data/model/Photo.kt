package com.pavellukyanov.themartian.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sol")
    val sol: Long,
    @SerializedName("camera")
    val camera: Camera,
    @SerializedName("img_src")
    val srcPhoto: String,
    @SerializedName("earth_date")
    val dataEarth: String,
    @SerializedName("rover")
    val rover: Rover
): Parcelable
