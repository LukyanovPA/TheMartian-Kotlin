package com.pavellukyanov.themartian.data.api

import android.util.Log
import com.pavellukyanov.themartian.utils.Constants.Companion.ALTERNATIVE_API_KEY_VALUE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import com.pavellukyanov.themartian.utils.Constants.Companion.API_KEY_VALUE
import com.pavellukyanov.themartian.utils.Constants.Companion.API_KEY
import com.pavellukyanov.themartian.utils.Constants.Companion.BASE_URL
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
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

        val alternativeUrl = chain.request().url
            .newBuilder()
            .addQueryParameter(API_KEY, ALTERNATIVE_API_KEY_VALUE)
            .build()

        val alternativeRequest = chain.request()
            .newBuilder()
            .url(alternativeUrl)
            .build()

        var response: Response? = null

        try {
            response = chain.proceed(newRequest)
            return@Interceptor response
        } catch (e: Exception) {
            e.message?.let { Log.d("Router", it) }
            response?.close()
            response = chain.proceed(alternativeRequest)
            return@Interceptor response
        } finally {
            response?.close()
            Log.d("Router", "Finally block")
        }
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
