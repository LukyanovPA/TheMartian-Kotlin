package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.models.database.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.network.RoverInfo
import kotlinx.coroutines.*

class MainRepoImpl (
    private val dataBaseRepoInterface: DataBaseRepoInterface,
    private val networkManifestRepoInterface: NetworkRepoInterface
) : MainRepoInterface {
    private var _networkData: List<RoverInfo> = emptyList()
    private var _datadaseData: MutableLiveData<List<RoverInfoEntity>> = MutableLiveData()
    private var databaseData: MutableList<RoverInfo> = mutableListOf()
    private val handler = CoroutineExceptionHandler(handler = { _, error ->
        Log.d(LOG_TAG, "${error.message}")
    })
    private val scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.IO +
                handler
    )

    private suspend fun getRoverInfoInNetwork() {
        scope.async { _networkData = networkManifestRepoInterface.getRoverManifest() }
            .await()
    }

    private suspend fun getRoverInfoInDatabase() {
        scope.launch {
            dataBaseRepoInterface.getAllRoverInfo().observeForever( { _datadaseData.postValue(it) } )
            _datadaseData.value?.let {
               it.forEach {
                   Log.d("ttt", "Main repo - ${it.name}")
                   databaseData.add(transformRoverInfoEntityForDefault(it))
               }
            }
        }
    }

    override suspend fun getRoversInfo(): List<RoverInfo> {
        getRoverInfoInDatabase()
        getRoverInfoInNetwork()
        insertRoverInDatabase(_networkData)
        return databaseData.toList()
    }

    private suspend fun insertRoverInDatabase(listRoverInfo: List<RoverInfo>) {
        dataBaseRepoInterface.setRoverInfo(listRoverInfo)
    }

    private fun transformRoverInfoEntityForDefault(roverInfoEntity: RoverInfoEntity): RoverInfo {
        val roverInfo = RoverInfo(
            roverInfoEntity.name,
            roverInfoEntity.landingDate,
            roverInfoEntity.launchData,
            roverInfoEntity.status,
            roverInfoEntity.maxSol,
            roverInfoEntity.maxDate,
            roverInfoEntity.totalPhotos,
            //временно
            listOf()
        )
        roverInfo.mainPagePicture.apply {
            when (roverInfo.name) {
                CURIOSITY -> R.drawable.curiosity
                OPPORTUNITY -> R.drawable.opportunity
                SPIRIT -> R.drawable.spirit
            }
        }
        roverInfo.detailsPagePicture.apply {
            when (roverInfo.name) {
                CURIOSITY -> R.drawable.curiosity_rover
                OPPORTUNITY -> R.drawable.opportunity_rover
                SPIRIT -> R.drawable.spirit_rover
            }
        }
        return roverInfo
    }

    companion object {
        private const val LOG_TAG = "FinallyRepo"
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}