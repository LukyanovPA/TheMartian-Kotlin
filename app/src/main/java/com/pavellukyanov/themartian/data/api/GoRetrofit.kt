package com.pavellukyanov.themartian.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoRetrofit {
    private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/"
    private const val API_KEY = "f8FYngXOCFmWPVOgmcugDO5JwAsPB238oee4wh6V"

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val nasaClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .build()

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(nasaClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiNASA = getRetrofit().create(ApiNASA::class.java)
}
