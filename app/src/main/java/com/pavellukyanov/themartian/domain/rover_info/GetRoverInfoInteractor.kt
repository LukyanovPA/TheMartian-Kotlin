package com.pavellukyanov.themartian.domain.rover_info

import io.reactivex.Single
import javax.inject.Inject

interface GetRoverInfoInteractor : (String) -> Single<RoverInfo>

class GetRoverInfoInteractorImpl @Inject constructor(
    private val roverInfoRepo: RoverInfoRepo
) : GetRoverInfoInteractor {
    override fun invoke(roverName: String): Single<RoverInfo> =
        roverInfoRepo.getRoverInfo(roverName)
}