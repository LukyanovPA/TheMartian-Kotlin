package com.pavellukyanov.themartian.data.mapper.roverinfo

import com.pavellukyanov.themartian.data.database.models.RoverInfoEntity
import com.pavellukyanov.themartian.domain.rover_info.RoverInfo
import com.pavellukyanov.themartian.data.mapper.Mapper

class RoverInfoEntityToDomain : Mapper<RoverInfoEntity, RoverInfo> {
    override fun invoke(source: RoverInfoEntity): RoverInfo =
        RoverInfo(
            source.roverName,
            source.landingDate,
            source.launchData,
            source.status,
            source.maxSol,
            source.maxDate,
            source.totalPhotos
        )
}