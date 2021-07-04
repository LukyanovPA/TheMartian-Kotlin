package com.pavellukyanov.themartian.data.mapper.photomapper

import com.pavellukyanov.themartian.data.database.models.FavouriteEntity
import com.pavellukyanov.themartian.domain.Photo
import com.pavellukyanov.themartian.data.mapper.Mapper

class PhotoDomainToFavourites : Mapper<Photo, FavouriteEntity> {
    override fun invoke(source: Photo): FavouriteEntity =
        FavouriteEntity(
            source.id,
            source.sol,
            source.srcPhoto,
            source.dataEarth,
            source.rover,
            source.camera
        )
}