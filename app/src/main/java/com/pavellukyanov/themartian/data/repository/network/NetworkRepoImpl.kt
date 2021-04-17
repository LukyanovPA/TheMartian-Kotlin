package com.pavellukyanov.themartian.data.repository.network

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.utils.Constants.Companion.CURIOSITY
import com.pavellukyanov.themartian.utils.Constants.Companion.OPPORTUNITY
import com.pavellukyanov.themartian.utils.Constants.Companion.SPIRIT
import com.pavellukyanov.themartian.utils.Constants.Companion.PERSEVERANCE
import dagger.Provides
import javax.inject.Inject

class NetworkRepoImpl @Inject constructor(
    private val apiService: ApiNASA
) : NetworkRepo {

    override suspend fun getRoverManifest(): List<RoverInfo> {
        val roverNames: List<String> = listOf(CURIOSITY, OPPORTUNITY, SPIRIT, PERSEVERANCE)
        val finallyList = mutableListOf<RoverInfo>()
        roverNames.forEach {
            finallyList.add(getRoverManifestInNetwork(it))
        }
        return finallyList
    }

    override suspend fun getPhotoForSol(roverName: String, sol: Long): Mars =
        apiService.getPhoto(roverName, sol)

    override suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars =
        apiService.getPhotoEarthData(roverName, earthData)

    private suspend fun getRoverManifestInNetwork(roverName: String): RoverInfo =
        apiService.getRoverManifest(roverName).photoManifest

}