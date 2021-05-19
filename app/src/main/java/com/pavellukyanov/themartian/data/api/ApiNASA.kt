package com.pavellukyanov.themartian.data.api

import com.pavellukyanov.themartian.data.api.models.MarsApi
import com.pavellukyanov.themartian.data.api.models.RoverManifestApi
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiNASA {

    @GET("rovers/{rover}/photos?")
    fun getPhotoEarthData(
        @Path("rover") roverName: String,
        @Query("earth_date") earthDate: String,
    ): Single<MarsApi>

    @GET("manifests/{rover}/?")
    fun getRoverManifest(
        @Path("rover") roverName: String
    ): Single<RoverManifestApi>
}