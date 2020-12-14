package com.pavellukyanov.themartian.data.api

class ApiManifestHelper(private val apiManifest: ApiNASA) {

    suspend fun getRoverManifest(roverName: String) = apiManifest.getRoverManifest(roverName)
}