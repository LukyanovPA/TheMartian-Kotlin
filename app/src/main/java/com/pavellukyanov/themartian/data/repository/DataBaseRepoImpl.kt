package com.pavellukyanov.themartian.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.DataBase
import com.pavellukyanov.themartian.data.models.dbmodel.RoverInfoEntity
import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo
import com.pavellukyanov.themartian.ui.main.helpers.GetRoverInfos
import kotlinx.coroutines.*

class DataBaseRepoImpl(private val db: DataBase) : DataBaseRepoInterface {
    private val handler = CoroutineExceptionHandler(handler = { _, error ->
        Log.d("ttt", "${error.message}")
    })
    private val scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.IO +
                handler
    )
    private lateinit var obseTest: RoverInfoEntity


    override suspend fun setRoverInfo(roverInfoList: List<RoverInfo>) {
        roverInfoList.forEach {
            val rovInf = initDefaultRoverInfo(it)
            Log.d("ttt", "Insert rovInfo - ${rovInf.name}")


            val test2 = db.roverInfoDao().getAllRoversInfo()
            Log.d("ttt", "Test 2 - ${test2}")


            val test = db.roverInfoDao().getRoverInfo(1)
            obseTest = initRoverInfoEntity(it)
            scope.launch {
                test.observeForever({ obseTest })
            }

            Log.d("ttt", "Test - ${obseTest.name}")

            if (test == null) {
                db.roverInfoDao().insertRoverInfo(initRoverInfoEntity(rovInf))
//                Log.d("ttt", "get baza 2 - ${getRoverInfo(rovInf.name).value?.name}")
            } else {
                db.roverInfoDao().updateRoverInfo(initRoverInfoEntity(rovInf))
            }
        }
    }

    override suspend fun deleteRoverInfo(roverName: String) {
        db.roverInfoDao().deleteRoverInfo(roverName)
    }

    override fun clearAllBase() = db.clearAllTables()

    override suspend fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>> {
        return db.roverInfoDao().getAllRoversInfo()
    }

    private fun initDefaultRoverInfo(roverInfo: RoverInfo): RoverInfo {
        return when (roverInfo.name) {
            CURIOSITY -> roverInfo.apply {
                roverInfo.roverPicture = GetRoverInfos.curRoverPic
                roverInfo.picture = GetRoverInfos.curPic
            }
            OPPORTUNITY -> roverInfo.apply {
                roverInfo.roverPicture = GetRoverInfos.oppRovPic
                roverInfo.picture = GetRoverInfos.oppPic
            }
            SPIRIT -> roverInfo.apply {
                roverInfo.roverPicture = GetRoverInfos.spirRovPic
                roverInfo.picture = GetRoverInfos.spirPic
            }
            else -> roverInfo
        }
    }

    private fun initRoverInfoEntity(roverInfo: RoverInfo): RoverInfoEntity {
        return RoverInfoEntity(
            roverInfo.name,
            roverInfo.roverPicture,
            roverInfo.picture,
            roverInfo.landingDate,
            roverInfo.launchData,
            roverInfo.status,
            roverInfo.maxSol,
            roverInfo.maxDate,
            roverInfo.photos.size
        )
    }

    companion object {
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}