package com.pavellukyanov.themartian.data.api.repository

import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import io.reactivex.Single

interface NetworkRepo {
    fun getPerseverance(): Single<RoverInfo>
    fun getSpirit(): Single<RoverInfo>
    fun getOpportunity(): Single<RoverInfo>
    fun getCuriosity(): Single<RoverInfo>

    fun getRoverInfo(roverName: String): Single<RoverInfo>

    fun getPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>
}