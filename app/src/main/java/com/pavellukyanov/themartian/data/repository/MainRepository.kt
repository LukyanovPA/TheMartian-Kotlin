package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {
    suspend fun getPhoto(roverName: String, sol: Long) = apiHelper.getPhoto(roverName, sol)

    suspend fun getPhotoEarthData(roverName: String, earthData: String) = apiHelper.getPhotoEarhData(roverName, earthData)
}