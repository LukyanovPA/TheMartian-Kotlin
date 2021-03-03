package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.R
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.model.Mars
import com.pavellukyanov.themartian.data.model.RoverInfo

class NetworkRepoImpl(
    private val apiHelper: ApiHelper,
    private val apiManifestHelper: ApiManifestHelper
) : NetworkRepoInterface {
    override suspend fun getRoverManifest(): List<RoverInfo> {
        val roverNames: List<String> = listOf(CURIOSITY, OPPORTUNITY, SPIRIT)
        val finallyList = mutableListOf<RoverInfo>()
        roverNames.forEach {
            finallyList.add(getRoverManifestInNetwork(it))
        }
        return finallyList
    }

    override suspend fun getPhotoForSol(roverName: String, sol: Long): Mars =
        apiHelper.getPhotoForSol(roverName, sol)

    override suspend fun getPhotoForEarthDate(roverName: String, earthData: String): Mars =
        apiHelper.getPhotoForEarthData(roverName, earthData)

    private suspend fun getRoverManifestInNetwork(roverName: String): RoverInfo =
        apiManifestHelper.getRoverManifest(roverName).photoManifest

    companion object {
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}