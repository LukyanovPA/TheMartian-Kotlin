package com.pavellukyanov.themartian.core.worker

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

class RoverInfoUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : Worker(appContext, params) {

    @Inject
    lateinit var roverInfoRepo: RoverInfoRepo

    override fun doWork(): Result {
        try {
            roverInfoRepo.setRoverInfoFromWorker()
            Log.d("ttt", "Worker work!")
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }

    class Factory @Inject constructor(
    ) : ChildWorkerFactory {

        override fun create(appContext: Context, params: WorkerParameters): ListenableWorker {
            return RoverInfoUpdateWorker(
                appContext,
                params
            )
        }
    }
}