package com.pavellukyanov.themartian.data.database.repo

import com.pavellukyanov.themartian.data.database.MartianDatabase
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo
import com.pavellukyanov.themartian.data.mapper.roverinfomapper.RoverInfoDomainToEntity
import com.pavellukyanov.themartian.data.mapper.roverinfomapper.RoverInfoEntityToDomain
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoverInfoDatabaseImpl @Inject constructor(
    private val database: MartianDatabase
) : RoverInfoDatabase {
    override fun getRoverInfo(roverName: String): Single<RoverInfo> {
        return database.roverInfoDao().getRoverInfo(roverName)
            .subscribeOn(Schedulers.io())
            .map { RoverInfoEntityToDomain().invoke(it) }
    }

    override fun getAllRoverInfo(): Single<List<RoverInfo>> {
//        database.clearAllTables()
        return database.roverInfoDao().getAllRoverInfo()
            .subscribeOn(Schedulers.io())
            .map { mappingEntityToDomain(it) }
    }

    private fun mappingEntityToDomain(entityList: List<RoverInfoEntity>): List<RoverInfo> {
        val returnList = mutableListOf<RoverInfo>()
        entityList.forEach {
            returnList.add(RoverInfoEntityToDomain().invoke(it))
        }
        return returnList
    }

    override fun insertRoverInfo(roverInfo: RoverInfo): Completable {
        return database.roverInfoDao().insertRoverInfo(
            RoverInfoDomainToEntity().invoke(roverInfo)
        )
    }

    override fun updateRoverInfo(roverInfoEntity: RoverInfoEntity): Completable {
        return database.roverInfoDao().updateRoverInfo(roverInfoEntity)
    }

    override fun deleteRoverInfo(roverName: String): Completable {
        return database.roverInfoDao().deleteRoverInfo(roverName)
    }
}