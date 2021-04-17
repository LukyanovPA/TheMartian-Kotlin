package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity

class PhotoMapper : Mapper<Photo, PhotoEntity> {
    override fun invoke(source: Photo): PhotoEntity {
        return PhotoEntity(
            source.id,
            source.sol,
            source.srcPhoto,
            source.dataEarth,
            source.rover.roverName
        )
    }
}