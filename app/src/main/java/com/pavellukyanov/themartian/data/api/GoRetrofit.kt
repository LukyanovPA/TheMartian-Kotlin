package com.pavellukyanov.themartian.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoRetrofit {
    private const val baseUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiNASA = getRetrofit().create(ApiNASA::class.java)
}
