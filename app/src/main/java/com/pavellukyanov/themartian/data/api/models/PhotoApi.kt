package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class PhotoApi(
    @SerializedName("id")
    val id: Long,
    @SerializedName("sol")
    val sol: Long,
    @SerializedName("camera")
    val cameraApi: CameraApi,
    @SerializedName("img_src")
    val srcPhoto: String,
    @SerializedName("earth_date")
    val dataEarth: String,
    @SerializedName("rover")
    val roverApi: RoverApi
): Parcelable
