package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.domain.photo.Photo
import io.reactivex.Single
import javax.inject.Inject

interface GetAllFavouritesPhotoInteractor : () -> Single<List<Photo>>

class GetAllFavouritesPhotoInteractorImpl @Inject constructor(
    private val favouritePhotoRepo: FavouritePhotoRepo
) : GetAllFavouritesPhotoInteractor {
    override fun invoke(): Single<List<Photo>> =
        Single.fromObservable(favouritePhotoRepo.getAllFavouritePhoto())
}