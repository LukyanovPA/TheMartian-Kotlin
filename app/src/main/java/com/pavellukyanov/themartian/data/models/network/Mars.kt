package com.pavellukyanov.themartian.data.models.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Mars(
    @SerializedName("photos")
    val photos: ArrayList<Photo>
): Parcelable