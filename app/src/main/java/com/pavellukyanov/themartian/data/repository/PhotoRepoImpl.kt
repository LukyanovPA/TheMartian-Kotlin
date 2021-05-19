package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.api.repo.NetworkRepo
import com.pavellukyanov.themartian.data.database.repo.PhotoDatabase
import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
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
                    return@flatMap photoDatabase.getPhotoWithRoverNameAndDate(roverName, earthData)
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

    override fun getAllFavouritePhoto(): Single<List<Photo>> =
        photoDatabase.getFavouritesPhoto()
            .subscribeOn(Schedulers.io())
            .map { it }

    override fun addPhotoToFavourite(photo: Photo): Completable =
        Completable.fromAction {
            photoDatabase.addToFavourite(photo)
        }

    override fun deletePhotoInFavourite(photo: Photo): Completable =
        Completable.fromAction {
            photoDatabase.deleteInFavourite(photo)
        }

    override fun checkFavourite(id: Long): Single<Boolean> =
        photoDatabase.chekFavourite(id)
            .subscribeOn(Schedulers.io())
            .map { it }
}