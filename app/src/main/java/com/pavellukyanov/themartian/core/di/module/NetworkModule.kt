package com.pavellukyanov.themartian.core.di.module

import android.content.Context
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ApiNASA = RouterModule.apiService

    @Provides
    @Singleton
    fun provideNetworkMonitor(context: Context): NetworkMonitor = NetworkMonitorImpl(context)
}