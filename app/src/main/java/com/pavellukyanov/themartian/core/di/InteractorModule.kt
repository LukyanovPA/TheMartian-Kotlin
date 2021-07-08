package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractor
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractorImpl
import com.pavellukyanov.themartian.domain.photo.PhotoRepo
import com.pavellukyanov.themartian.domain.rover_info.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object InteractorModule {
    @Singleton
    @Provides
    fun provideLoadAllRoverInfoInteractor(
        roverInfoRepo: RoverInfoRepo
    ): LoadAllRoverInfoInteractor = LoadAllRoverInfoInteractorImpl(roverInfoRepo)

    @Singleton
    @Provides
    fun provideGetRoverInfoInteractor(
        roverInfoRepo: RoverInfoRepo
    ): GetRoverInfoInteractor = GetRoverInfoInteractorImpl(roverInfoRepo)

    @Singleton
    @Provides
    fun provideLoadPhotoForEarthDateInteractor(
        photoRepo: PhotoRepo
    ): LoadPhotoForEarthDateInteractor = LoadPhotoForEarthDateInteractorImpl(photoRepo)
}