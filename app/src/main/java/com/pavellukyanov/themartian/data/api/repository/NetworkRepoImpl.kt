package com.pavellukyanov.themartian.data.api.repository

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.models.MarsApi
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import com.pavellukyanov.themartian.data.mapper.photo.PhotoPojoToDomain
import com.pavellukyanov.themartian.data.mapper.roverinfo.RoverInfoPojoToDomain
import com.pavellukyanov.themartian.utils.Constants.Companion.CURIOSITY
import com.pavellukyanov.themartian.utils.Constants.Companion.OPPORTUNITY
import com.pavellukyanov.themartian.utils.Constants.Companion.PERSEVERANCE
import com.pavellukyanov.themartian.utils.Constants.Companion.SPIRIT
import io.reactivex.Single
import javax.inject.Inject

class NetworkRepoImpl @Inject constructor(
    private val apiService: ApiNASA
) : NetworkRepo {

    override fun getRoverInfo(roverName: String): Single<RoverInfo> =
        apiService.getRoverManifest(roverName)
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }

    override fun getCuriosity(): Single<RoverInfo> =
        apiService.getRoverManifest(CURIOSITY)
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }

    override fun getPerseverance(): Single<RoverInfo> =
        apiService.getRoverManifest(PERSEVERANCE)
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }

    override fun getSpirit(): Single<RoverInfo> =
        apiService.getRoverManifest(SPIRIT)
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }

    override fun getOpportunity(): Single<RoverInfo> =
        apiService.getRoverManifest(OPPORTUNITY)
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }

    override fun getPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>> =
        apiService.getPhotoEarthData(roverName, earthData)
            .map { it.toListPhoto() }
}

fun MarsApi.toListPhoto(): List<Photo> {
    val listPhoto = mutableListOf<Photo>()
    this.photoApis.forEach {
        listPhoto.add(PhotoPojoToDomain().invoke(it))
    }
    return listPhoto
}