package com.pavellukyanov.themartian.data.mapper.photomapper

import com.pavellukyanov.themartian.data.api.models.PhotoApi
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.mapper.Mapper

class PhotoPojoToDomain : Mapper<PhotoApi, Photo> {
    override fun invoke(source: PhotoApi): Photo {
        return Photo(
            source.id,
            source.cameraApi.name,
            source.srcPhoto,
            source.dataEarth,
            source.sol,
            source.roverApi.roverName,
            0
        )
    }
}