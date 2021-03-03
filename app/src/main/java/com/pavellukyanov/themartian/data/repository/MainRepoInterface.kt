package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.models.network.RoverInfo

interface MainRepoInterface {
    suspend fun getRoversInfo(): List<RoverInfo>
}