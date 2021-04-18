package com.pavellukyanov.themartian.data.repository

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface MainRepo {
    suspend fun setRoverInfoFromWorker()

    suspend fun getRoverInfo(roverName: String): RoverInfoEntity

    suspend fun getRoverManifest(): LiveData<List<RoverInfoEntity>>

    suspend fun getPhotoForSol(roverName: String, sol: Long): Mars

    suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars

    suspend fun getAllFavouritePhoto(): LiveData<List<PhotoEntity>>

    suspend fun insertPhotoToFavourite(photo: Photo)

    suspend fun deletePhotoInFavourite(id: Long)
}