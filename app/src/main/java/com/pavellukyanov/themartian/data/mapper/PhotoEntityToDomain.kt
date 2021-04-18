package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto

class PhotoEntityToDomain : Mapper<PhotoEntity, DomainPhoto> {
    override fun invoke(source: PhotoEntity): DomainPhoto {
        return DomainPhoto(
            source.id,
            source.camera,
            source.srcPhoto,
            source.dataEarth,
            source.sol,
            source.rover
        )
    }
}