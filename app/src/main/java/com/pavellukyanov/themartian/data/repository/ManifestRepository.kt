package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.api.ApiManifestHelper

class ManifestRepository(private val manifestHelper: ApiManifestHelper) {

    suspend fun getRoverManifest(roverName: String) = manifestHelper.getRoverManifest(roverName)
}