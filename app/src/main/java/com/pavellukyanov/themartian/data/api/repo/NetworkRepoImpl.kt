package com.pavellukyanov.themartian.data.api.repo

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.models.MarsApi
import com.pavellukyanov.themartian.data.api.models.RoverInfoApi
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.mapper.PhotoPojoToDomain
import com.pavellukyanov.themartian.data.mapper.RoverInfoEntityToDomain
import com.pavellukyanov.themartian.data.mapper.RoverInfoPojoToDomain
import io.reactivex.Observable
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

    override fun getPhotoForEarthDate(roverName: String, earthData: String): Single<MarsApi> {
       return apiService.getPhotoEarthData(roverName, earthData)
           .subscribeOn(Schedulers.io())
           .map { it }
    }
}