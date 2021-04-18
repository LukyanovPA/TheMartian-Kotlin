package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.domain.DomainPhoto

class PhotoToDomain : Mapper<Photo, DomainPhoto> {
    override fun invoke(source: Photo): DomainPhoto {
        return DomainPhoto(
            source.id,
            source.camera.name,
            source.srcPhoto,
            source.dataEarth,
            source.sol,
            source.rover.roverName
        )
    }
}