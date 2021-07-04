package com.pavellukyanov.themartian.data.database.repo

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.domain.RoverInfo
import io.reactivex.Observable

interface RoverInfoDatabase {
    fun getRoverInfo(roverName: String): Observable<RoverInfo>

    fun getAllRoverInfo(): Observable<List<RoverInfo>>

    fun insertRoverInfo(roverInfo: RoverInfo)

    fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    fun deleteRoverInfo(roverName: String)
}