package com.pavellukyanov.themartian.data.mapper

import com.pavellukyanov.themartian.data.api.models.Photo
import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.DomainPhoto

class DomainPhotoToEntity : Mapper<DomainPhoto, PhotoEntity> {
    override fun invoke(source: DomainPhoto): PhotoEntity {
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