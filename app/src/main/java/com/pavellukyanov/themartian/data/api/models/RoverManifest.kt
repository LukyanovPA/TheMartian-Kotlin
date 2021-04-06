package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

//Manifest Rover data class
@Parcelize
class RoverManifest(
    @SerializedName("photo_manifest")
    val photoManifest: RoverInfo
): Parcelable