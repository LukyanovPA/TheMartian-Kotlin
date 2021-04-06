package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class RoverInfo(
    @SerializedName("name")
    var name: String,
    @SerializedName("landing_date")
    var landingDate: String,
    @SerializedName("launch_date")
    var launchData: String,
    @SerializedName("status")
    var status: String,
    @SerializedName("max_sol")
    var maxSol: Long,
    @SerializedName("max_date")
    var maxDate: String,
    @SerializedName("total_photos")
    var totalPhotos: Long,
    @SerializedName("photos")
    var photos: List<PhotosInfo>
) : Parcelable