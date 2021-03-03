package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.models.network.RoverInfo

interface NetworkRepoInterface {
    suspend fun getRoverManifest(): List<RoverInfo>

}