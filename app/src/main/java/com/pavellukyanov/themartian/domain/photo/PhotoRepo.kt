package com.pavellukyanov.themartian.domain.photo

import io.reactivex.Observable
import io.reactivex.Single

interface PhotoRepo {

    fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>

    fun getAllFavouritePhoto(): Observable<List<Photo>>

    fun addPhotoToFavourite(photo: Photo)

    fun deletePhotoInFavourite(photo: Photo)

    fun checkFavourite(id: Long): Observable<Boolean>
}