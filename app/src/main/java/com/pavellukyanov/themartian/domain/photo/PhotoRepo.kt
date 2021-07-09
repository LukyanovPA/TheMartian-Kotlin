package com.pavellukyanov.themartian.domain.photo

import io.reactivex.Single

interface PhotoRepo {
    fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>
}