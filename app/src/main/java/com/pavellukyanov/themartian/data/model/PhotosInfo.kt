package com.pavellukyanov.themartian.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PhotosInfo(
    @SerializedName("sol")
    val sol: Long,
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("total_photos")
    val totalPhotos: Long,
    @SerializedName("cameras")
    val cameras: MutableList<String>
) : Parcelable