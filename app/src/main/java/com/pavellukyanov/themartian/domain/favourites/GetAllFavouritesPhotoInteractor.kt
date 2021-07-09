package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Observable
import javax.inject.Inject

interface GetAllFavouritesPhotoInteractor : () -> Observable<List<Photo>>

class GetAllFavouritesPhotoInteractorImpl @Inject constructor(
    private val favouritePhotoRepo: FavouritePhotoRepo
) : GetAllFavouritesPhotoInteractor {
    override fun invoke(): Observable<List<Photo>> =
        favouritePhotoRepo.getAllFavouritePhoto()
}