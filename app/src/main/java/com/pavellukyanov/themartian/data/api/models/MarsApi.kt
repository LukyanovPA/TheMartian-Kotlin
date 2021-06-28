package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
class MarsApi(
    @SerialName("photos")
    val photoApis: ArrayList<PhotoApi>
) : Parcelable