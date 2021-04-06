package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.data.repository.database.DatabaseRepo
import com.pavellukyanov.themartian.data.repository.network.NetworkRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


class MainRepoImpl @Inject constructor(
    private val databaseRepo: DatabaseRepo,
    private val networkRepo: NetworkRepo
) : MainRepo {

    override suspend fun getRoverManifest(): List<RoverInfo> = networkRepo.getRoverManifest()

    override suspend fun getPhotoForSol(roverName: String, sol: Long): Mars {
        TODO("Not yet implemented")
    }

    override suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars {
        TODO("Not yet implemented")
    }
}