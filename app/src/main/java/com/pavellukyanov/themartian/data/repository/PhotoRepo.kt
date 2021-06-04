package com.pavellukyanov.themartian.data.repository

import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface PhotoRepo {

    fun loadPhotoForEarthDate(roverName: String, earthData: String): Single<List<Photo>>

    fun getAllFavouritePhoto(): Observable<List<Photo>>

    fun addPhotoToFavourite(photo: Photo)

    fun deletePhotoInFavourite(photo: Photo)

    fun checkFavourite(id: Long): Observable<Boolean>
}