package com.pavellukyanov.themartian.core.di

import com.pavellukyanov.themartian.data.repository.MainRepo
import com.pavellukyanov.themartian.data.repository.MainRepoImpl
import com.pavellukyanov.themartian.data.repository.database.DatabaseRepoImpl
import com.pavellukyanov.themartian.data.repository.network.NetworkRepoImpl
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
    fun provideMainRepo(database: DatabaseRepoImpl, networkRepo: NetworkRepoImpl): MainRepo = MainRepoImpl(database, networkRepo)
}