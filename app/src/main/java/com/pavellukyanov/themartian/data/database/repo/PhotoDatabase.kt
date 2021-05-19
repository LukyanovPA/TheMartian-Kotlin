package com.pavellukyanov.themartian.data.database.repo

import com.pavellukyanov.themartian.data.database.models.PhotoEntity
import com.pavellukyanov.themartian.data.domain.Photo
import io.reactivex.Completable
import io.reactivex.Single

interface PhotoDatabase {
    fun getPhoto(id: Long): Single<Photo>

    fun getFavouritesPhoto(): Single<List<Photo>>

    fun getPhotoWithRoverNameAndDate(roverName: String, earthDate: String): Single<List<Photo>>

    fun insertPhoto(photoEntity: Photo): Completable

    fun updatePhoto(photoEntity: Photo): Completable

    fun deletePhoto(id: Long): Completable

    fun addToFavourite(photo: Photo): Completable

    fun deleteInFavourite(photo: Photo): Completable

    fun chekFavourite(id: Long): Single<Boolean>
}