package com.pavellukyanov.themartian.favourites

import com.pavellukyanov.themartian.base.BaseTest
import com.pavellukyanov.themartian.domain.favourites.FavouritePhotoRepo
import com.pavellukyanov.themartian.domain.favourites.GetAllFavouritesPhotoInteractor
import com.pavellukyanov.themartian.domain.favourites.GetAllFavouritesPhotoInteractorImpl
import com.pavellukyanov.themartian.domain.photo.Photo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test

class GetAllFavouritesPhotoInteractorTest : BaseTest<List<Photo>>() {
    private val favouritePhotoRepo = mockk<FavouritePhotoRepo>()
    private val listPhoto = emptyList<Photo>()

    private lateinit var underTest: GetAllFavouritesPhotoInteractor

    @Before
    fun startTest() {
        underTest = GetAllFavouritesPhotoInteractorImpl(favouritePhotoRepo)
    }

    @Test
    fun `response complete`() {
        every { favouritePhotoRepo.getAllFavouritePhoto() } returns Observable.just(listPhoto)

        underTest.invoke()
            .subscribe(testObserver)
        testObserver.assertValue(listPhoto)

        verify { favouritePhotoRepo.getAllFavouritePhoto() }
    }
}