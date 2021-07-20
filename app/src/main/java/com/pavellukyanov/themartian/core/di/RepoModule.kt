package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.core.networkmonitor.NetworkMonitorImpl
import com.pavellukyanov.themartian.data.api.ApiNASA
import com.pavellukyanov.themartian.data.api.repository.NetworkRepo
import com.pavellukyanov.themartian.data.api.repository.NetworkRepoImpl
import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabaseImpl
import com.pavellukyanov.themartian.data.database.repository.rover_info.RoverInfoDatabase
import com.pavellukyanov.themartian.data.database.repository.rover_info.RoverInfoDatabaseImpl
import com.pavellukyanov.themartian.data.repository.favourites.FavouritePhotoRepoImpl
import com.pavellukyanov.themartian.data.repository.photo.PhotoRepoImpl
import com.pavellukyanov.themartian.data.repository.rover_info.RoverInfoRepoImpl
import com.pavellukyanov.themartian.domain.favourites.FavouritePhotoRepo
import com.pavellukyanov.themartian.domain.photo.PhotoRepo
import com.pavellukyanov.themartian.domain.rover_info.RoverInfoRepo
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

    @Singleton
    @Provides
    fun provideFavouritePhotoRepo(
        database: PhotoDatabase
    ): FavouritePhotoRepo = FavouritePhotoRepoImpl(database)
}