package com.pavellukyanov.themartian.data.database.repository.rover_info

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import io.reactivex.Observable

interface RoverInfoDatabase {
    fun getRoverInfo(roverName: String): Observable<RoverInfo>

    fun getAllRoverInfo(): Observable<List<RoverInfo>>

    fun insertRoverInfo(roverInfo: RoverInfo)

    fun updateRoverInfo(roverInfoEntity: RoverInfoEntity)

    fun deleteRoverInfo(roverName: String)
}