package com.pavellukyanov.themartian.data.mapper.photomapper

import com.pavellukyanov.themartian.data.api.models.PhotoApi
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.mapper.Mapper

class PhotoPojoToEntity : Mapper<PhotoApi, PhotoEntity> {
    override fun invoke(source: PhotoApi): PhotoEntity =
        PhotoEntity(
            source.id,
            source.sol,
            source.srcPhoto,
            source.dataEarth,
            source.roverApi.roverName,
            source.cameraApi.name
        )
}