package com.pavellukyanov.themartian.domain.favourites

import com.pavellukyanov.themartian.base.BaseTest
import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.photo.Photo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class AddPhotoToFavouriteInteractorTest : BaseTest<Completable>() {
    private val photoDatabase = mockk<PhotoDatabase>()
    private val photo = mockk<Photo>()

    private lateinit var underTest: AddPhotoToFavouriteInteractor

    @Before
    fun startTest() {
        underTest = AddPhotoToFavouriteInteractorImpl(photoDatabase)
    }

    @Test
    fun `add photo to favourite complete`() {
        every { photoDatabase.addToFavourite(any()) } returns Completable.complete()

        underTest.invoke(photo)
            .subscribe(testObserver)
        testObserver.assertResult()

        verify { photoDatabase.addToFavourite(photo) }
    }
}