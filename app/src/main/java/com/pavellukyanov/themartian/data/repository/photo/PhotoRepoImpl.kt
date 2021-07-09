package com.pavellukyanov.themartian.data.repository.photo

import com.pavellukyanov.themartian.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.api.repository.NetworkRepo
import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.domain.photo.PhotoRepo
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.*
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val photoDatabase: PhotoDatabase,
    private val networkRepo: NetworkRepo,
    private val networkMonitor: NetworkMonitor
) : PhotoRepo {

    override fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>> {
        return getPhotoToEarthDate(roverName, earthData)
    }

    private fun getPhotoToEarthDate(roverName: String, earthData: String): Single<List<Photo>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { networkAvailable ->
                if (!networkAvailable) {
                    return@flatMap Single.fromObservable(
                        photoDatabase.getPhotoWithRoverNameAndDate(
                            roverName,
                            earthData
                        )
                    )
                } else {
                    return@flatMap networkRepo.getPhotoForEarthDate(roverName, earthData)
                        .doOnSuccess { success ->
                            insertPhotoInDatabase(success)
                        }
                }
            }
    }

    private fun insertPhotoInDatabase(roverInfoList: List<Photo>) {
        roverInfoList.forEach {
            photoDatabase.insertPhoto(it)
        }
    }
}