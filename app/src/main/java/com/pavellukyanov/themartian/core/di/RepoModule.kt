package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabaseImpl
import com.pavellukyanov.themartian.data.api.repo.NetworkRepoImpl
import com.pavellukyanov.themartian.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepoModule {

    @Singleton
    @Provides
    fun provideRoverInfoRepo(
        database: RoverInfoDatabaseImpl,
        networkRepo: NetworkRepoImpl,
        networkMonitorImpl: NetworkMonitorImpl
    ): RoverInfoRepo = RoverInfoRepoImpl(database, networkRepo, networkMonitorImpl)

    @Singleton
    @Provides
    fun providePhotoRepo(
        database: RoverInfoDatabaseImpl,
        networkRepo: NetworkRepoImpl,
        networkMonitorImpl: NetworkMonitorImpl
    ): PhotoRepo = PhotoRepoImpl(database, networkRepo, networkMonitorImpl)

}