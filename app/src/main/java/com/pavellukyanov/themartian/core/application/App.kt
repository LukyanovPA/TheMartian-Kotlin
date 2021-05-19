package com.pavellukyanov.themartian.core.application

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.*
import com.pavellukyanov.themartian.core.di.AppComponent
import com.pavellukyanov.themartian.core.di.DaggerAppComponent
import com.pavellukyanov.themartian.core.di.DaggerViewModelComponent
import com.pavellukyanov.themartian.core.di.ViewModelComponent
import com.pavellukyanov.themartian.utils.Constants
import com.pavellukyanov.themartian.core.worker.RoverInfoUpdateWorker
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class App : Application(), Configuration.Provider, HasAndroidInjector {
    companion object {

        lateinit var instance: App
        lateinit var appComponent: AppComponent

        fun applicationContext(): Context = instance
    }

    var viewModelComponent: ViewModelComponent? = null

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    lateinit var workerFactory: WorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        viewModelComponent = DaggerViewModelComponent.create()
        instance = this
        DaggerAppComponent.builder()
            .application(this)
            .build()
            .inject(this)

        initWorker()
    }

    private fun initWorker() {
        setupWorker()
        Log.d("ttt", "Worker")
    }

    private fun setupWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RoverInfoUpdateWorker>(2, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            Constants.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )

//        WorkManager.initialize(this, workManagerConfiguration)
    }
}