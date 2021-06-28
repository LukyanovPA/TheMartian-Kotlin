package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class PhotosInfoApi(
    @SerialName("sol")
    val sol: Long,
    @SerialName("earth_date")
    val earthDate: String,
    @SerialName("total_photos")
    val totalPhotos: Long,
    @SerialName("cameras")
    val cameras: MutableList<String>
) : Parcelable