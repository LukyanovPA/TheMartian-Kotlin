package com.pavellukyanov.themartian.data.repository.database

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.repository.database.DatabaseRepo
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

class DatabaseRepoImpl @Inject constructor(
    private val database: MartianDatabase
    ) : DatabaseRepo {
    override suspend fun getRoverInfo(roverName: String): RoverInfoEntity =
        database.roverInfoDao().getRoverInfo(roverName)

    override fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>> {
//        database.clearAllTables()
        return database.roverInfoDao().getAllRoverInfo()
    }

    override suspend fun insertRoverInfo(roverInfoEntity: RoverInfoEntity) {
        database.roverInfoDao().insertRoverInfo(roverInfoEntity)
    }

    override suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity) {
        database.roverInfoDao().updateRoverInfo(roverInfoEntity)
    }

    override suspend fun deleteRoverInfo(roverName: String) {
        database.roverInfoDao().deleteRoverInfo(roverName)
    }
}