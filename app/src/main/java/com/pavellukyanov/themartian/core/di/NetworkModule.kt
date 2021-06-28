package com.pavellukyanov.themartian.core.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.pavellukyanov.themartian.BuildConfig
import com.pavellukyanov.themartian.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.core.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.themartian.data.api.ApiNASA
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/"
    private const val API_KEY_VALUE = "pHdYPhiUm6t4QTKaHT9uEpB4NdIee7SU5du7heHU"
    private const val API_KEY = "api_key"

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
            .apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
            }

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }

        okHttpBuilder.addInterceptor {
            val request = it.request()
            val url = request.url
                .newBuilder()
                .addQueryParameter(API_KEY, API_KEY_VALUE)
                .build()
            it.proceed(request.newBuilder().url(url).build())
        }

        val contentType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
        }
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpBuilder.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiNASA(retrofit: Retrofit): ApiNASA = retrofit.create(ApiNASA::class.java)

    @Provides
    @Singleton
    fun provideNetworkMonitor(@ApplicationContext context: Context): NetworkMonitor =
        NetworkMonitorImpl(context)
}