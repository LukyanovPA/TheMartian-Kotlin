package com.pavellukyanov.themartian.domain.photo

import io.reactivex.Single
import javax.inject.Inject

interface LoadPhotoForEarthDateInteractor : (String, String) -> Single<List<Photo>>

class LoadPhotoForEarthDateInteractorImpl @Inject constructor(
    private val photoRepo: PhotoRepo
) : LoadPhotoForEarthDateInteractor {
    override fun invoke(roverName: String, earthDate: String): Single<List<Photo>> =
        photoRepo.loadPhotoForEarthDate(roverName, earthDate)
}