package com.pavellukyanov.themartian.data.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.pavellukyanov.themartian.utils.Constants.Companion.API_KEY_VALUE
import com.pavellukyanov.themartian.utils.Constants.Companion.API_KEY
import com.pavellukyanov.themartian.utils.Constants.Companion.BASE_URL
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Router {

    private val authInterceptor = Interceptor { chain ->
        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(API_KEY, API_KEY_VALUE)
            .build()

        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()

        chain.proceed(newRequest)
    }

    private val nasaClient = OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(nasaClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiNASA = getRetrofit().create(ApiNASA::class.java)
}
