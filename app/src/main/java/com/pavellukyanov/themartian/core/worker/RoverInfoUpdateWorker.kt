package com.pavellukyanov.themartian.core.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pavellukyanov.themartian.data.repository.MainRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class RoverInfoUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params){

    @Inject lateinit var mainRepo: MainRepo

    override suspend fun doWork(): Result {
        try {
            mainRepo.setRoverInfoFromWorker()
            Log.d("ttt", "Worker work!")
        } catch (e: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
}