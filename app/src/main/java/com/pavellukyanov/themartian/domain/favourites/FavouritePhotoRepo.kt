package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface FavouritePhotoRepo {
    fun getAllFavouritePhoto(): Observable<List<Photo>>

    fun addPhotoToFavourite(photo: Photo): Completable

    fun deletePhotoInFavourite(photo: Photo): Completable

    fun checkFavourite(id: Long): Observable<Boolean>
}