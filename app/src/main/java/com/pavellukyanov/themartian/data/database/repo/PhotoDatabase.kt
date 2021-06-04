package com.pavellukyanov.themartian.data.database.repo

import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Observable
import io.reactivex.Single

interface PhotoDatabase {
    fun getPhoto(id: Long): Observable<Photo>

    fun getFavouritesPhoto(): Observable<List<Photo>>

    fun getPhotoWithRoverNameAndDate(roverName: String, earthDate: String): Observable<List<Photo>>

    fun insertPhoto(photoEntity: Photo)

    fun deletePhoto(id: Long)

    fun addToFavourite(photo: Photo)

    fun deleteInFavourite(photo: Photo)

    fun chekFavourite(id: Long): Observable<Boolean>
}