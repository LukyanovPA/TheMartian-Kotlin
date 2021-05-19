package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class MarsApi(
    @SerializedName("photos")
    val photoApis: ArrayList<PhotoApi>
): Parcelable