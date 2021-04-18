package com.pavellukyanov.themartian.data.repository.database

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface DatabaseRepo {
    suspend fun getRoverInfo(roverName: String): RoverInfoEntity

    suspend fun getAllRoverInfo(): LiveData<List<RoverInfoEntity>>

    suspend fun insertRoverInfo(roverInfoEntity: RoverInfoEntity)

    suspend fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    suspend fun deleteRoverInfo(roverName: String)

    suspend fun getPhoto(id: Long): PhotoEntity

    suspend fun getAllPhoto(): LiveData<List<PhotoEntity>>

    suspend fun insertPhoto(photoEntity: PhotoEntity)

    suspend fun updatePhoto(photoEntity: PhotoEntity)

    suspend fun deletePhoto(id: Long)
}