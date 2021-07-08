package com.pavellukyanov.themartian.domain.rover_info

import io.reactivex.Single
import javax.inject.Inject

interface LoadAllRoverInfoInteractor : () -> Single<List<RoverInfo>>

class LoadAllRoverInfoInteractorImpl @Inject constructor(
    private val roverInfoRepo: RoverInfoRepo
) : LoadAllRoverInfoInteractor {
    override fun invoke(): Single<List<RoverInfo>> =
        roverInfoRepo.loadAllRoverInfo()
}