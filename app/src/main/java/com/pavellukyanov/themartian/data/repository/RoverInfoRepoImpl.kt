package com.pavellukyanov.themartian.data.repository

import android.util.Log
import com.pavellukyanov.themartian.core.networkmonitor.NetworkMonitor
import com.pavellukyanov.themartian.data.database.repo.RoverInfoDatabase
import com.pavellukyanov.themartian.data.api.repo.NetworkRepo
import com.pavellukyanov.themartian.data.domain.RoverInfo
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import javax.inject.Inject


class RoverInfoRepoImpl @Inject constructor(
    private val roverInfoDatabase: RoverInfoDatabase,
    private val networkRepo: NetworkRepo,
    private val networkMonitor: NetworkMonitor
) : RoverInfoRepo {
    companion object {
        private const val LOG_TAG = "MainRepo"
    }

    private var tempRoverInfoList = mutableListOf<RoverInfo>()

    override fun setRoverInfoFromWorker(): Completable {
        return Completable.fromAction {
            getAllRoverInfoInApi()
                .subscribe { listRoverInfo ->
                    insertRoverInfoInDatabase(listRoverInfo)
                }
        }
    }

    override fun getRoverInfo(roverName: String): Single<RoverInfo> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { networkAvailable ->
                if (!networkAvailable) {
                    return@flatMap Single.fromObservable(
                        roverInfoDatabase.getRoverInfo(roverName)
                    )
                } else {
                    return@flatMap networkRepo.getRoverInfo(roverName)
                }
            }
    }

    override fun loadAllRoverInfo(): Single<List<RoverInfo>> {
        return getAllRoverInfo()
    }

    private fun getAllRoverInfo(): Single<List<RoverInfo>> {
        return Single.just(networkMonitor.isNetworkAvailable())
            .flatMap { networkAvailable ->
                if (!networkAvailable) {
                    Log.d("ttt", "baza")
                    return@flatMap Single.fromObservable(
                        roverInfoDatabase.getAllRoverInfo()
                    )
                } else {
                    return@flatMap getAllRoverInfoInApi()
                        .doOnSuccess { success ->
                            Log.d("ttt", "network ${success.size}")
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

    private fun getAllRoverInfoInApi(): Single<List<RoverInfo>> {
        return Single.zip(
            networkRepo.getPerseverance().subscribeOn(Schedulers.io()),
            networkRepo.getCuriosity().subscribeOn(Schedulers.io()),
            networkRepo.getOpportunity().subscribeOn(Schedulers.io()),
            networkRepo.getSpirit().subscribeOn(Schedulers.io())
        ) { pers, cur, oppo, spirit ->
            addRoverInfoToList(pers, cur, oppo, spirit)
        }
    }

    private fun addRoverInfoToList(
        one: RoverInfo,
        two: RoverInfo,
        three: RoverInfo,
        four: RoverInfo
    ): List<RoverInfo> {
        tempRoverInfoList.add(one)
        tempRoverInfoList.add(two)
        tempRoverInfoList.add(three)
        tempRoverInfoList.add(four)
        Log.d("ttt", "addList - ${tempRoverInfoList.size}")
        return tempRoverInfoList
    }
}