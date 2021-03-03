package com.pavellukyanov.themartian.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Camera(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("rover_id")
    val roverID: Long,
    @SerializedName("full_name")
    val fuulName: String
): Parcelable