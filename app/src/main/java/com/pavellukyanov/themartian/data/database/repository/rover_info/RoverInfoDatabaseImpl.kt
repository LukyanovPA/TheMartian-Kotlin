package com.pavellukyanov.themartian.data.database.repository.rover_info

import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import com.pavellukyanov.themartian.data.mapper.roverinfo.RoverInfoDomainToEntity
import com.pavellukyanov.themartian.data.mapper.roverinfo.RoverInfoEntityToDomain
import io.reactivex.Observable
import javax.inject.Inject

class RoverInfoDatabaseImpl @Inject constructor(
    private val database: MartianDatabase
) : RoverInfoDatabase {
    override fun getRoverInfo(roverName: String): Observable<RoverInfo> {
        return database.roverInfoDao().getRoverInfo(roverName)
            .map { RoverInfoEntityToDomain().invoke(it) }
    }

    override fun getAllRoverInfo(): Observable<List<RoverInfo>> {
//        database.clearAllTables()
        return database.roverInfoDao().getAllRoverInfo()
            .map { mappingEntityToDomain(it) }
    }

    private fun mappingEntityToDomain(entityList: List<RoverInfoEntity>): List<RoverInfo> {
        val returnList = mutableListOf<RoverInfo>()
        entityList.forEach {
            returnList.add(RoverInfoEntityToDomain().invoke(it))
        }
        return returnList
    }

    override fun insertRoverInfo(roverInfo: RoverInfo) {
        database.roverInfoDao().insertRoverInfo(
            RoverInfoDomainToEntity().invoke(roverInfo)
        )
    }

    override fun updateRoverInfo(roverInfoEntity: RoverInfoEntity) {
        database.roverInfoDao().updateRoverInfo(roverInfoEntity)
    }

    override fun deleteRoverInfo(roverName: String) {
        database.roverInfoDao().deleteRoverInfo(roverName)
    }
}