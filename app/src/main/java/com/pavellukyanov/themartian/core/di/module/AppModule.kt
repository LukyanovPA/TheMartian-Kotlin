package com.pavellukyanov.themartian.core.di.module

import android.content.Context
import com.pavellukyanov.themartian.core.application.App
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return App.applicationContext()
    }

    @Provides
    @Singleton
    fun provideNetworkMonitor(context: Context): NetworkMonitor = NetworkMonitorImpl(context)
}