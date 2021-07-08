package com.pavellukyanov.themartian.data.mapper.photo

import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.data.mapper.Mapper

class PhotoDomainToEntity : Mapper<Photo, PhotoEntity> {
    override fun invoke(source: Photo): PhotoEntity {
        return PhotoEntity(
            source.id,
            source.sol,
            source.srcPhoto,
            source.dataEarth,
            source.rover,
            source.camera
        )
    }
}