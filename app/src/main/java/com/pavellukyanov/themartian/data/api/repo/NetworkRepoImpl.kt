package com.pavellukyanov.themartian.data.api.repo

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.models.MarsApi
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.mapper.photomapper.PhotoPojoToDomain
import com.pavellukyanov.themartian.data.mapper.roverinfomapper.RoverInfoPojoToDomain
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NetworkRepoImpl @Inject constructor(
    private val apiService: ApiNASA
) : NetworkRepo {

    override fun getRoverInfo(roverName: String): Single<RoverInfo> {
        return apiService.getRoverManifest(roverName)
            .subscribeOn(Schedulers.io())
            .map { RoverInfoPojoToDomain().invoke(it.photoManifest) }
    }

    override fun getPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>> {
        return apiService.getPhotoEarthData(roverName, earthData)
            .subscribeOn(Schedulers.io())
            .map { mappingPhotoPojoToDomain(it) }
    }

    private fun mappingPhotoPojoToDomain(marsApi: MarsApi): List<Photo> {
        val listPhoto = mutableListOf<Photo>()
        marsApi.photoApis.forEach {
            listPhoto.add(PhotoPojoToDomain().invoke(it))
        }
        return listPhoto
    }
}