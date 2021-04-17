package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.mapper.PhotoMapper
import com.pavellukyanov.themartian.data.mapper.PojoToEntity
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

    private val scope = CoroutineScope(
        Dispatchers.IO + SupervisorJob() + CoroutineExceptionHandler { _, e ->
            Log.d(LOG_TAG, "Exception - ${e.message}")
        })

    override suspend fun setRoverInfoFromWorker() {
        scope.launch {
            val roverInfoList = scope.async { networkRepo.getRoverManifest() }.await()
            roverInfoList.forEach {
                scope.launch {
                    databaseRepo.insertRoverInfo(PojoToEntity()(it))
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

    override suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars =
        networkRepo.getPhotoForEarthDate(roverName, earthData)

    override suspend fun getAllFavouritePhoto(): LiveData<List<PhotoEntity>> {
        return databaseRepo.getAllPhoto()
    }

    override suspend fun insertPhotoToFavourite(photo: Photo) {
        databaseRepo.insertPhoto(PhotoMapper()(photo))
    }

    override suspend fun deletePhotoInFavourite(id: Long) {
        databaseRepo.deletePhoto(id)
    }
}