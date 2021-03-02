package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.models.networkmodel.RoverInfo

interface NetworkManifestRepoInterface {
    suspend fun getRoverManifest(): List<RoverInfo>
}