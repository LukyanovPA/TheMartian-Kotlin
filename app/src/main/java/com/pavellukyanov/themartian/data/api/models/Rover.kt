package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Rover(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val roverName: String,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchData: String,
    @SerializedName("status")
    val status: String
): Parcelable
