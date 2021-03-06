package com.pavellukyanov.themartian.data.repository.network

import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.RoverInfo

interface NetworkRepo {
    suspend fun getRoverManifest(): List<RoverInfo>

    suspend fun getPhotoForSol(roverName: String, sol: Long): Mars

    suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars
}