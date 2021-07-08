package com.pavellukyanov.themartian.domain.rover_info

import io.reactivex.Completable
import io.reactivex.Single

interface RoverInfoRepo {
    fun setRoverInfoFromWorker(): Completable

    fun getRoverInfo(roverName: String): Single<RoverInfo>

    fun loadAllRoverInfo(): Single<List<RoverInfo>>
}