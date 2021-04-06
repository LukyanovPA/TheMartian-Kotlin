package com.pavellukyanov.themartian.data.api

import com.pavellukyanov.themartian.data.api.models.Mars
import com.pavellukyanov.themartian.data.api.models.RoverManifest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNASA {

    @GET("rovers/{rover}/photos?")
    suspend fun getPhoto(
        @Path("rover") roverName: String,
        @Query("sol") sol: Long,
    ): Mars

    @GET("rovers/{rover}/photos?")
    suspend fun getPhotoEarthData(
        @Path("rover") roverName: String,
        @Query("earth_date") earthDate: String,
    ): Mars

    @GET("manifests/{rover}/?")
    suspend fun getRoverManifest(
        @Path("rover") roverName: String
    ): RoverManifest
}