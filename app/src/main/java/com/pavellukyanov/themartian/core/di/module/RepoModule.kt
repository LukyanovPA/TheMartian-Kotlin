package com.pavellukyanov.themartian.core.di.module

import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabaseImpl
import com.pavellukyanov.themartian.data.api.repo.NetworkRepoImpl
import com.pavellukyanov.themartian.data.database.repo.PhotoDatabaseImpl
import com.pavellukyanov.themartian.data.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

}