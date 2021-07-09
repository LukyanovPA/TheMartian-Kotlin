package com.pavellukyanov.themartian.data.database.repository.photo

import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import io.reactivex.Observable

interface PhotoDatabase {
    fun getPhoto(id: Long): Observable<Photo>

    fun getFavouritesPhoto(): Observable<List<Photo>>

    fun getPhotoWithRoverNameAndDate(roverName: String, earthDate: String): Observable<List<Photo>>

    fun insertPhoto(photoEntity: Photo)

    fun deletePhoto(id: Long)

    fun addToFavourite(photo: Photo): Completable

    fun deleteInFavourite(photo: Photo): Completable

    fun chekFavourite(id: Long): Observable<Boolean>
}