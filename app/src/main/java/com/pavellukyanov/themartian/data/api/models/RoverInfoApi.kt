package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class RoverInfoApi(
    @SerialName("name")
    var name: String,
    @SerialName("landing_date")
    var landingDate: String,
    @SerialName("launch_date")
    var launchData: String,
    @SerialName("status")
    var status: String,
    @SerialName("max_sol")
    var maxSol: Long,
    @SerialName("max_date")
    var maxDate: String,
    @SerialName("total_photos")
    var totalPhotos: Long,
    @SerialName("photos")
    var photos: List<PhotosInfoApi>
) : Parcelable