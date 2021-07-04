package com.pavellukyanov.themartian.data.mapper.roverinfomapper

import com.pavellukyanov.themartian.data.api.models.RoverInfoApi
import com.pavellukyanov.themartian.domain.RoverInfo
import com.pavellukyanov.themartian.data.mapper.Mapper

class RoverInfoPojoToDomain : Mapper<RoverInfoApi, RoverInfo> {
    override fun invoke(source: RoverInfoApi): RoverInfo =
        RoverInfo(
            source.name,
            source.landingDate,
            source.launchData,
            source.status,
            source.maxSol.toString(),
            source.maxDate,
            source.totalPhotos.toString()
        )
}