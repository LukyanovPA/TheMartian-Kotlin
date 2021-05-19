package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Completable
import io.reactivex.Single

interface PhotoRepo {

    fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>

    fun getAllFavouritePhoto(): Single<List<Photo>>

    fun insertPhotoToFavourite(photo: Photo): Completable

    fun deletePhotoInFavourite(id: Long): Completable

    fun checkFavourite(id: Long): Single<Boolean>
}