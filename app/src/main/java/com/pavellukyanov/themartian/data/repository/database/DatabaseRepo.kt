package com.pavellukyanov.themartian.data.repository.database

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface DatabaseRepo {
    suspend fun getRoverInfo(roverName: String): RoverInfoEntity

    fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>>

    suspend fun insertRoverInfo(roverInfoEntity: RoverInfoEntity)

    suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    suspend fun deleteRoverInfo(roverName: String)
}