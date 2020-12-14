package com.pavellukyanov.themartian.data.api

import com.pavellukyanov.themartian.data.model.Mars
import com.pavellukyanov.themartian.data.model.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*
import kotlin.collections.ArrayList

interface ApiNASA {

    @GET("{rover}/photos?")
    suspend fun getPhoto(
        @Path("rover") roverName: String,
        @Query("sol") sol: Long,
//        @Query("api_key") apiKey: String
    ): Mars

    @GET("{rover}/photos?")
    suspend fun getPhotoEarthData(
        @Path("rover") roverName: String,
        @Query("earth_date") earthDate: String,
//        @Query("api_key") apiKey: String
    ): Mars

}