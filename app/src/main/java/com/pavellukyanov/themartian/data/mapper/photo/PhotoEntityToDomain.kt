package com.pavellukyanov.themartian.data.mapper.photo

import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.domain.photo.Photo
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