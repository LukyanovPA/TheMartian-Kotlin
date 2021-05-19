package com.pavellukyanov.themartian.data.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Photo(
    val id: Long,
    val camera: String,
    val srcPhoto: String,
    val dataEarth: String,
    val sol: Long,
    val rover: String,
    var isFavourite: Int
): Parcelable