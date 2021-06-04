package com.pavellukyanov.themartian.core.di.module

import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.themartian.data.api.repo.NetworkRepo
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabaseImpl
import com.pavellukyanov.themartian.data.api.repo.NetworkRepoImpl
import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.repo.PhotoDatabase
import com.pavellukyanov.themartian.data.database.repo.PhotoDatabaseImpl
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabase
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
        database: PhotoDatabaseImpl,
        networkRepo: NetworkRepoImpl,
        networkMonitorImpl: NetworkMonitorImpl
    ): PhotoRepo = PhotoRepoImpl(database, networkRepo, networkMonitorImpl)

    @Singleton
    @Provides
    fun provideNetworkRepo(
        apiNASA: ApiNASA
    ): NetworkRepo = NetworkRepoImpl(apiNASA)

    @Singleton
    @Provides
    fun providePhotoDatabase(
        database: MartianDatabase
    ): PhotoDatabase = PhotoDatabaseImpl(database)

    @Singleton
    @Provides
    fun provideRoverInfoDatabase(
        database: MartianDatabase
    ): RoverInfoDatabase = RoverInfoDatabaseImpl(database)
}