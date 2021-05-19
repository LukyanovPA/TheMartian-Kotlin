package com.pavellukyanov.themartian.core.di.module

import androidx.work.WorkerFactory
import com.pavellukyanov.themartian.core.di.keys.WorkerKey
import com.pavellukyanov.themartian.core.worker.ChildWorkerFactory
import com.pavellukyanov.themartian.core.worker.DaggerWorkerFactory
import com.pavellukyanov.themartian.core.worker.RoverInfoUpdateWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(RoverInfoUpdateWorker::class)
    fun bindPrepopulateCategoryWork(factory: RoverInfoUpdateWorker.Factory): ChildWorkerFactory

    @Binds
    fun bindWorkManagerFactory(factory: DaggerWorkerFactory): WorkerFactory
}