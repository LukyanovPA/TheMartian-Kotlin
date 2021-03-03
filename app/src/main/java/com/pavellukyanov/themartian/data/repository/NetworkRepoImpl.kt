package com.pavellukyanov.themartian.data.repository

import android.util.Log
import com.pavellukyanov.themartian.data.api.ApiHelper
import com.pavellukyanov.themartian.data.api.ApiManifestHelper
import com.pavellukyanov.themartian.data.models.network.RoverInfo

class NetworkRepoImpl(
    private val manifestHelper: ApiManifestHelper,
    private val apiHelper: ApiHelper
    ) : NetworkRepoInterface {

    suspend fun getRoverManifestInNetwork(roverName: String): RoverInfo =
        manifestHelper.getRoverManifest(roverName).photoManifest

    override suspend fun getRoverManifest(): List<RoverInfo> {
        val roverNames: List<String> = listOf(CURIOSITY, OPPORTUNITY, SPIRIT)
        val finnalyList = mutableListOf<RoverInfo>()
        roverNames.forEach {
            finnalyList.add(getRoverManifestInNetwork(it))
            Log.d("ttt", "Запрос к api - $it")
        }
        Log.d("ttt", "Response - ${finnalyList[0].landingDate}")
        return finnalyList
    }

    companion object {
        private const val CURIOSITY = "Curiosity"
        private const val OPPORTUNITY = "Opportunity"
        private const val SPIRIT = "Spirit"
    }
}