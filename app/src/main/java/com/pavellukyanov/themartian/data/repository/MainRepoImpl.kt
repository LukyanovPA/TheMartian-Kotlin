package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.mapper.PojoToEntity
import com.pavellukyanov.themartian.data.repository.database.DatabaseRepo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton


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

    override suspend fun getRoverManifest(): LiveData<List<RoverInfoEntity>> {
        Log.d("ttt", "Main repo ${databaseRepo.getAllRoverInfo().value?.size}")
        return databaseRepo.getAllRoverInfo()
    }

    override suspend fun getPhotoForSol(roverName: String, sol: Long): Mars =
        networkRepo.getPhotoForSol(roverName, sol)

    override suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars =
        networkRepo.getPhotoForEarthDate(roverName, earthData)
}