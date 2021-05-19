package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Completable
import io.reactivex.Single

interface PhotoRepo {

    fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>

    fun getAllFavouritePhoto(): Single<List<Photo>>

    fun addPhotoToFavourite(photo: Photo): Completable

    fun deletePhotoInFavourite(photo: Photo): Completable

    fun checkFavourite(id: Long): Single<Boolean>
}