package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class RoverApi(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val roverName: String,
    @SerialName("landing_date")
    val landingDate: String,
    @SerialName("launch_date")
    val launchData: String,
    @SerialName("status")
    val status: String
) : Parcelable
