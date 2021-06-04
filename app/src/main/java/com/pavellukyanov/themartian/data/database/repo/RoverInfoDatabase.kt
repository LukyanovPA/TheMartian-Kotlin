package com.pavellukyanov.themartian.data.database.repo

import androidx.lifecycle.LiveData
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

interface RoverInfoDatabase {
    fun getRoverInfo(roverName: String): Observable<RoverInfo>

    fun getAllRoverInfo(): Observable<List<RoverInfo>>

    fun insertRoverInfo(roverInfo: RoverInfo)

    fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    fun deleteRoverInfo(roverName: String)
}