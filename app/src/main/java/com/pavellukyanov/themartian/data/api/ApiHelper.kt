package com.pavellukyanov.themartian.data.api

class ApiHelper(private val apiService: ApiNASA) {
    suspend fun getPhoto(roverName: String, sol: Long) = apiService.getPhoto(roverName, sol/*, API_KEY*/)

    suspend fun getPhotoEarhData(roverName: String, earthDate: String) = apiService.getPhotoEarthData(roverName, earthDate/*, API_KEY*/)

//    companion object {
//        private const val API_KEY = "f8FYngXOCFmWPVOgmcugDO5JwAsPB238oee4wh6V"
//    }
}