package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.models.database.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.network.RoverInfo
import com.pavellukyanov.themartian.ui.main.adapter.MainAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class DataBaseRepoImpl(private val db: DataBase) : DataBaseRepoInterface {
    private val handler = CoroutineExceptionHandler(handler = { _, error ->
        Log.d("ttt", "${error.message}")
    })
    private val scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main +
                handler
    )

    override suspend fun setRoverInfo(roverInfoList: List<RoverInfo>) {
        roverInfoList.forEach {
            if (db.roverInfoDao().getRoverInfo(it.name) != it.name) {
                scope.launch { db.roverInfoDao().insertRoverInfo(transformRoverInfoForEntity(it)) }
            } else {
                if (db.roverInfoDao().getRoverMaxDate(it.name) != it.maxDate) {
                    scope.launch {
                        db.roverInfoDao().updateRoverInfo(transformRoverInfoForEntity(it))
                    }
                }
            }
        }
    }

    private fun clearAllBase() = db.clearAllTables()

    override suspend fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>> {
        return db.roverInfoDao().getAllRoversInfo()
    }

    private fun transformRoverInfoForEntity(roverInfo: RoverInfo): RoverInfoEntity {
        val roverInfoEntity = RoverInfoEntity(
            roverInfo.name,
            roverInfo.landingDate,
            roverInfo.launchData,
            roverInfo.status,
            roverInfo.maxSol,
            roverInfo.maxDate,
            roverInfo.totalPhotos
        )
        roverInfoEntity.mainPagePicture.apply {
            when (roverInfoEntity.name) {
                CURIOSITY -> R.drawable.curiosity
                OPPORTUNITY -> R.drawable.opportunity
                SPIRIT -> R.drawable.spirit
            }
        }
        roverInfoEntity.detailsPagePicture.apply {
            when (roverInfoEntity.name) {
                CURIOSITY -> R.drawable.curiosity_rover
                OPPORTUNITY -> R.drawable.opportunity_rover
                SPIRIT -> R.drawable.spirit_rover
            }
        }
        return roverInfoEntity
    }

    companion object {
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}