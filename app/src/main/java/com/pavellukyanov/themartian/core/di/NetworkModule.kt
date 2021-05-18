package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ApiNASA = Router.apiService
}