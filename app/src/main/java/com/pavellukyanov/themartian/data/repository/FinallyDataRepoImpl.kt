package com.pavellukyanov.themartian.data.repository

import android.text.TextUtils.isEmpty
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo
import com.pavellukyanov.themartian.ui.main.helpers.GetRoverInfos
import kotlinx.coroutines.*

class FinallyDataRepoImpl(
    private val dataBaseRepoInterface: DataBaseRepoInterface,
    private val networkManifestRepoInterface: NetworkManifestRepoInterface
) : FinallyDataRepoInterface {
    private var _networkData: List<RoverInfo> = emptyList()
    private var _databaseData: MutableLiveData<List<RoverInfoEntity>> = MutableLiveData()
    private var databaseData: LiveData<List<RoverInfoEntity>> = MutableLiveData()


    private var finallyData: MutableLiveData<List<RoverInfo>> = MutableLiveData()
    private var finallyList: MutableList<RoverInfo> = mutableListOf()
    private val handler = CoroutineExceptionHandler(handler = { _, error ->
        Log.d(LOG_TAG, "${error.message}")
    })
    private val scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.IO +
                handler
    )

    private suspend fun getNetworkData() {
        scope.async { _networkData = networkManifestRepoInterface.getRoverManifest() }
            .await()
    }

    override suspend fun getRoversInfo(): LiveData<List<RoverInfoEntity>> {
        getNetworkData()
        insertNetworkDataInDatabase(_networkData)

        return dataBaseRepoInterface.getAllRoverInfo()
    }

    private fun convertEntity(listRoverInfoEntity: List<RoverInfoEntity>): List<RoverInfo> {
        val convertList = mutableListOf<RoverInfo>()
        listRoverInfoEntity.forEach {
            convertList.add(
                RoverInfo(
                    it.name,
                    //временно
                    "DataBase",
                    it.launchData,
                    it.status,
                    it.maxSol,
                    it.maxDate,
                    it.totalPhotos.toLong(),
                    //временно
                    listOf()
                ).apply {
                    when (it.name) {
                        CURIOSITY -> {
                            this.roverPicture = GetRoverInfos.curRoverPic
                            this.picture = GetRoverInfos.curPic
                        }
                        OPPORTUNITY -> {
                            this.roverPicture = GetRoverInfos.oppRovPic
                            this.picture = GetRoverInfos.oppPic
                        }
                        SPIRIT -> {
                            this.roverPicture = GetRoverInfos.spirRovPic
                            this.picture = GetRoverInfos.spirPic
                        }
                    }
                }
            )
        }
        return convertList
    }

    private suspend fun insertNetworkDataInDatabase(listRoverInfo: List<RoverInfo>) {
        dataBaseRepoInterface.setRoverInfo(listRoverInfo)
    }

    companion object {
        private const val LOG_TAG = "FinallyRepo"
        const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}