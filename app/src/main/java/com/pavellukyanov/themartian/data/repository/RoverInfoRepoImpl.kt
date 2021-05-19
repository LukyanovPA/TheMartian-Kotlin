package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.api.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.mapper.RoverInfoPojoToEntity
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabase
import com.pavellukyanov.themartian.data.api.repo.NetworkRepo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.utils.Constants.Companion.ROVER_NAMES
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RoverInfoRepoImpl @Inject constructor(
    private val roverInfoDatabase: RoverInfoDatabase,
    private val networkRepo: NetworkRepo,
    private val networkMonitor: NetworkMonitor
) : RoverInfoRepo {
    companion object {
        private const val LOG_TAG = "MainRepo"
    }

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var tempRoverInfoList = mutableListOf<RoverInfo>()

    override fun setRoverInfoFromWorker(): Completable {
        return Completable.fromAction {
            setupAllRoverNamesInApi()
                .subscribe { listRoverInfo ->
                    insertRoverInfoInDatabase(listRoverInfo)
                }
        }
    }

    override fun getRoverInfo(roverName: String): Single<RoverInfo> {
        return roverInfoDatabase.getRoverInfo(roverName)
            .map { it }
    }

    override fun loadAllRoverInfo(): Single<List<RoverInfo>> {
        return getAllRoverInfo()
    }

    private fun getAllRoverInfo(): Single<List<RoverInfo>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { networkAvailable ->
                if (!networkAvailable) {
                    return@flatMap roverInfoDatabase.getAllRoverInfo()
                } else {
                    return@flatMap setupAllRoverNamesInApi()
                        .doOnSuccess { success ->
                            insertRoverInfoInDatabase(success)
                        }
                }
            }
    }

    private fun insertRoverInfoInDatabase(roverInfoList: List<RoverInfo>) {
        roverInfoList.forEach {
            roverInfoDatabase.insertRoverInfo(it)
        }
    }

    private fun setupAllRoverNamesInApi(): Single<List<RoverInfo>> {
        ROVER_NAMES.forEach {
            getRoverInfoInApi(it)
        }
        return Single.just(tempRoverInfoList)
    }

    private fun getRoverInfoInApi(roverName: String) {
        compositeDisposable.add(networkRepo.getRoverInfo(roverName)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe { response ->
                tempRoverInfoList.add(response)
            }
        )
        compositeDisposable.dispose()
    }
}