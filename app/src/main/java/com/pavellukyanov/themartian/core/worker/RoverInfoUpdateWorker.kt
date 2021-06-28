package com.pavellukyanov.themartian.core.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.pavellukyanov.themartian.data.repository.RoverInfoRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.reactivex.Single
import javax.inject.Inject

@HiltWorker
class RoverInfoUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : RxWorker(appContext, params) {

    @Inject
    lateinit var roverInfoRepo: RoverInfoRepo

    override fun createWork(): Single<Result> {
        return roverInfoRepo.setRoverInfoFromWorker()
            .doOnError { Result.retry() }
            .toSingleDefault(Result.success())
    }
}