package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import javax.inject.Inject

interface DeletePhotoInFavouriteInteractor : (Photo) -> Completable

class DeletePhotoInFavouriteInteractorImpl @Inject constructor(
    private val photoDatabase: PhotoDatabase
) : DeletePhotoInFavouriteInteractor {
    override fun invoke(photo: Photo): Completable {
        return Completable.fromAction {
            photoDatabase.deleteInFavourite(photo)
        }
    }
}