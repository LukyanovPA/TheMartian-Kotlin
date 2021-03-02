package com.pavellukyanov.themartian.data.api

import com.pavellukyanov.themartian.data.models.networkmodel.Mars
import com.pavellukyanov.themartian.data.models.networkmodel.RoverManifest
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNASA {

    @GET("{rover}/photos?")
    suspend fun getPhoto(
        @Path("rover") roverName: String,
        @Query("sol") sol: Long,
    ): Mars

    @GET("{rover}/photos?")
    suspend fun getPhotoEarthData(
        @Path("rover") roverName: String,
        @Query("earth_date") earthDate: String,
    ): Mars

    @GET("{rover}/?")
    suspend fun getRoverManifest(
        @Path("rover") roverName: String
    ): RoverManifest
}