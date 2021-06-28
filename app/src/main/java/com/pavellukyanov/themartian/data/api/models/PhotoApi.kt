package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class PhotoApi(
    @SerialName("id")
    val id: Long,
    @SerialName("sol")
    val sol: Long,
    @SerialName("camera")
    val cameraApi: CameraApi,
    @SerialName("img_src")
    val srcPhoto: String,
    @SerialName("earth_date")
    val dataEarth: String,
    @SerialName("rover")
    val roverApi: RoverApi
) : Parcelable
