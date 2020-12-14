package com.pavellukyanov.themartian.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Mars(
    @SerializedName("photos")
    val photos: ArrayList<Photo>
): Parcelable