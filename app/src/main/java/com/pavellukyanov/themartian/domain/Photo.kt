package com.pavellukyanov.themartian.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Photo(
    val id: Long,
    val camera: String,
    val srcPhoto: String,
    val dataEarth: String,
    val sol: Long,
    val rover: String
): Parcelable