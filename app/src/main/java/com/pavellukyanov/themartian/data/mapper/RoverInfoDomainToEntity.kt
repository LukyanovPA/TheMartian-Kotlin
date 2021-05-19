package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.data.domain.RoverInfo

class RoverInfoDomainToEntity : Mapper<RoverInfo, RoverInfoEntity> {
    override fun invoke(source: RoverInfo): RoverInfoEntity =
        RoverInfoEntity(
            source.roverName,
            source.landingDate,
            source.launchData,
            source.status,
            source.maxSol,
            source.maxDate,
            source.totalPhotos
        )
}