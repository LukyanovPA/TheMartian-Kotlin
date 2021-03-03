package com.pavellukyanov.themartian.data.api

class ApiHelper(private val apiService: ApiNASA) {
    suspend fun getPhotoForSol(roverName: String, sol: Long) = apiService.getPhoto(roverName, sol)

    suspend fun getPhotoForEarthData(roverName: String, earthDate: String) = apiService.getPhotoEarthData(roverName, earthDate)
}