package com.pavellukyanov.themartian.data.mapper.photomapper

import com.pavellukyanov.themartian.data.database.models.FavouriteEntity
import com.pavellukyanov.themartian.domain.Photo
import com.pavellukyanov.themartian.data.mapper.Mapper

class FavouritesToDomain : Mapper<FavouriteEntity, Photo> {
    override fun invoke(source: FavouriteEntity): Photo =
        Photo(
            source.id,
            source.camera,
            source.srcPhoto,
            source.dataEarth,
            source.sol,
            source.rover
        )
}