package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class CameraApi(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("rover_id")
    val roverID: Long,
    @SerialName("full_name")
    val fullName: String
) : Parcelable