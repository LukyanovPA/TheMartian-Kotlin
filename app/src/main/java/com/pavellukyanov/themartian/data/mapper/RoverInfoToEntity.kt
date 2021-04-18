package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.api.models.RoverInfo
import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity

class RoverInfoToEntity : Mapper<RoverInfo, RoverInfoEntity> {
    override fun invoke(source: RoverInfo): RoverInfoEntity {
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