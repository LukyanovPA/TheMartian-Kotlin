package com.pavellukyanov.themartian.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoverInfo(
    @SerializedName("name")
    val name: String,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchData: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("max_sol")
    val maxSol: Long,
    @SerializedName("max_date")
    val maxDate: String,
    @SerializedName("total_photos")
    val totalPhotos: Long,
    @SerializedName("photos")
    val photos: List<PhotosInfo>
) : Parcelable