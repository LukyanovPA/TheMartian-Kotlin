package com.pavellukyanov.themartian.data.repository.favourites

import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.favourites.FavouritePhotoRepo
import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class FavouritePhotoRepoImpl @Inject constructor(
    private val photoDatabase: PhotoDatabase
) : FavouritePhotoRepo {
    override fun getAllFavouritePhoto(): Observable<List<Photo>> =
        photoDatabase.getFavouritesPhoto()
            .map { it }

    override fun addPhotoToFavourite(photo: Photo): Completable {
        return Completable.fromAction {
            photoDatabase.addToFavourite(photo)
        }
    }

    override fun deletePhotoInFavourite(photo: Photo): Completable {
        return Completable.fromAction {
            photoDatabase.deleteInFavourite(photo)
        }
    }

    override fun checkFavourite(id: Long): Observable<Boolean> =
        photoDatabase.chekFavourite(id)
            .map { it }
}