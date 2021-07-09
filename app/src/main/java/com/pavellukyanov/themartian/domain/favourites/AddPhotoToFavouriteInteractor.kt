package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Completable
import javax.inject.Inject

interface AddPhotoToFavouriteInteractor : (Photo) -> Completable

class AddPhotoToFavouriteInteractorImpl @Inject constructor(
    private val photoDatabase: PhotoDatabase
) : AddPhotoToFavouriteInteractor {
    override fun invoke(photo: Photo): Completable =
        Completable.fromAction {
            photoDatabase.addToFavourite(photo)
        }
}