package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto
import com.pavellukyanov.themartian.data.mapper.PhotoToDomain
import com.pavellukyanov.themartian.data.mapper.DomainPhotoToEntity
import com.pavellukyanov.themartian.data.mapper.PhotoEntityToDomain
import com.pavellukyanov.themartian.data.mapper.RoverInfoToEntity
import com.pavellukyanov.themartian.data.repository.database.DatabaseRepo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import kotlinx.coroutines.*
import javax.inject.Inject


class MainRepoImpl @Inject constructor(
    private val databaseRepo: DatabaseRepo,
    private val networkRepo: NetworkRepo
) : MainRepo {
    companion object {
        private const val LOG_TAG = "MainRepo"
    }

    private var _domainFavourites: MutableLiveData<List<DomainPhoto>> = MutableLiveData()
    private val domainFavourites: LiveData<List<DomainPhoto>> get() = _domainFavourites
    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, e ->
            Log.d(LOG_TAG, "Exception - ${e.message}")
        })

    override suspend fun setRoverInfoFromWorker() {
        scope.launch {
            val roverInfoList = scope.async { networkRepo.getRoverManifest() }.await()
            roverInfoList.forEach {
                scope.launch {
                    databaseRepo.insertRoverInfo(RoverInfoToEntity()(it))
                }
            }
        }
    }

    override suspend fun getRoverInfo(roverName: String): RoverInfoEntity =
        databaseRepo.getRoverInfo(roverName)

    override suspend fun getRoverManifest(): LiveData<List<RoverInfoEntity>> {
        return databaseRepo.getAllRoverInfo()
    }

    override suspend fun getPhotoForSol(roverName: String, sol: Long): Mars =
        networkRepo.getPhotoForSol(roverName, sol)

    override suspend fun getPhotoForEarthDate(
        roverName: String,
        earthData: String
    ): List<DomainPhoto> {
        val domainModels = mutableListOf<DomainPhoto>()
        networkRepo.getPhotoForEarthDate(roverName, earthData).photos.forEach {
            domainModels.add(PhotoToDomain()(it))
        }
        return domainModels
    }

    override suspend fun getAllFavouritePhoto(): LiveData<List<DomainPhoto>> {
        scope.launch(Dispatchers.Main) {
            databaseRepo.getAllPhoto().observeForever { listEntity ->
                val domainList = mutableListOf<DomainPhoto>()
                listEntity.forEach {
                    domainList.add(PhotoEntityToDomain()(it))
                }
                _domainFavourites.postValue(domainList)
            }
        }
        return domainFavourites
    }

    override suspend fun insertPhotoToFavourite(photo: DomainPhoto) {
        databaseRepo.insertPhoto(DomainPhotoToEntity()(photo))
    }

    override suspend fun deletePhotoInFavourite(id: Long) {
        databaseRepo.deletePhoto(id)
    }

    override suspend fun checkFavourite(id: Long): Boolean {
        return databaseRepo.getPhoto(id) != null
    }
}