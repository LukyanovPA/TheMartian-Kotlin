package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.data.api.models.MarsApi
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.api.repo.NetworkRepo
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabase
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.mapper.PhotoDomainToEntity
import com.pavellukyanov.themartian.data.mapper.PhotoEntityToDomain
import com.pavellukyanov.themartian.data.mapper.PhotoPojoToDomain
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import javax.inject.Inject

class PhotoRepoImpl @Inject constructor(
    private val roverInfoDatabase: RoverInfoDatabase,
    private val networkRepo: NetworkRepo,
    private val networkMonitor: NetworkMonitor
) : PhotoRepo {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var tempRoverInfoList = mutableListOf<RoverInfo>()

    override fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>> {
        return getPhotoToEarthDate(roverName, earthData)

        val domainModels = mutableListOf<Photo>()
        networkRepo.getPhotoForEarthDate(roverName, earthData).photoApis.forEach {
            domainModels.add(PhotoPojoToDomain()(it))
        }
        return domainModels
    }

    private fun getPhotoToEarthDate(roverName: String, earthData: String): Single<List<Photo>> {

    }

    override fun getAllFavouritePhoto(): LiveData<List<Photo>> {
        scope.launch(Dispatchers.Main) {
            roverInfoDatabase.getAllPhoto().observeForever { listEntity ->
                val domainList = mutableListOf<Photo>()
                listEntity.forEach {
                    domainList.add(PhotoEntityToDomain()(it))
                }
                _Favourites.postValue(domainList)
            }
        }
        return favourites
    }

    override fun insertPhotoToFavourite(photo: Photo) {
        roverInfoDatabase.insertPhoto(PhotoDomainToEntity()(photo))
    }

    override fun deletePhotoInFavourite(id: Long) {
        roverInfoDatabase.deletePhoto(id)
    }

    override fun checkFavourite(id: Long): Boolean {
        return roverInfoDatabase.getPhoto(id) != null
    }
}