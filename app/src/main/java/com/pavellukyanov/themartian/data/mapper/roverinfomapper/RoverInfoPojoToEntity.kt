package com.pavellukyanov.themartian.data.mapper.roverinfomapper

import com.pavellukyanov.themartian.data.api.models.RoverInfoApi
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.mapper.Mapper

class RoverInfoPojoToEntity : Mapper<RoverInfoApi, RoverInfoEntity> {
    override fun invoke(source: RoverInfoApi): RoverInfoEntity {
        return RoverInfoEntity(
            source.name,
            source.landingDate,
            source.launchData,
            source.status,
            source.maxSol.toString(),
            source.maxDate,
            source.totalPhotos.toString()
        )
    }
}