package com.pavellukyanov.themartian.data.repository

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

interface MainRepo {

    suspend fun setRoverInfoFromWorker()

    suspend fun getRoverManifest(): LiveData<List<RoverInfoEntity>>

    suspend fun getPhotoForSol(roverName: String, sol: Long): Mars

    suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars
}