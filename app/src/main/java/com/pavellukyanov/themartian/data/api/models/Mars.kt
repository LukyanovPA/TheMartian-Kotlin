package com.pavellukyanov.themartian.data.api.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Mars(
    @SerializedName("photos")
    val photos: ArrayList<Photo>
): Parcelable