package com.pavellukyanov.themartian.data.mapper.photomapper

import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.Photo
import com.pavellukyanov.themartian.data.mapper.Mapper

class PhotoEntityToDomain : Mapper<PhotoEntity, Photo> {
    override fun invoke(source: PhotoEntity): Photo {
        return Photo(
            source.id,
            source.camera,
            source.srcPhoto,
            source.dataEarth,
            source.sol,
            source.rover
        )
    }
}