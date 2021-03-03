package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.model.Mars
import com.pavellukyanov.themartian.data.model.RoverInfo

interface NetworkRepoInterface {
    suspend fun getRoverManifest(): List<RoverInfo>

    suspend fun getPhotoForSol(roverName: String, sol: Long): Mars

    suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars
}