package com.pavellukyanov.themartian.core.application

import android.app.Application
import android.util.Log
import androidx.work.*
import androidx.hilt.work.HiltWorkerFactory
import com.pavellukyanov.themartian.utils.Constants
import com.pavellukyanov.themartian.core.worker.RoverInfoUpdateWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        initWorker()

        //удалить все таблицы из базы
//        this.applicationContext.deleteDatabase("MovieDatabase.db")
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
    }
}