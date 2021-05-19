package com.pavellukyanov.themartian.data.api.repo

import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import io.reactivex.Single

interface NetworkRepo {
    fun getRoverInfo(roverName: String): Single<RoverInfo>

    fun getPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>
}