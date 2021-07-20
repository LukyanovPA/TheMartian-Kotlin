package com.pavellukyanov.themartian.domain.favourites

import io.reactivex.Single
import javax.inject.Inject

interface CheckFavouritesInteractor : (Long) -> Single<Boolean>

class CheckFavouritesInteractorImpl @Inject constructor(
    private val favouritePhotoRepo: FavouritePhotoRepo
) : CheckFavouritesInteractor {
    override fun invoke(id: Long): Single<Boolean> =
        Single.fromObservable(favouritePhotoRepo.checkFavourite(id))
}