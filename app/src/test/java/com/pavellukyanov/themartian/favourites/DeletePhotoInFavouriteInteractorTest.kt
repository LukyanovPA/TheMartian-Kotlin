package com.pavellukyanov.themartian.favourites

import com.pavellukyanov.themartian.base.BaseTest
import com.pavellukyanov.themartian.data.database.repository.photo.PhotoDatabase
import com.pavellukyanov.themartian.domain.favourites.DeletePhotoInFavouriteInteractor
import com.pavellukyanov.themartian.domain.favourites.DeletePhotoInFavouriteInteractorImpl
import com.pavellukyanov.themartian.domain.photo.Photo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Completable
import org.junit.Before
import org.junit.Test

class DeletePhotoInFavouriteInteractorTest : BaseTest<Completable>() {
    private val photoDatabase = mockk<PhotoDatabase>()
    private val photo = mockk<Photo>()

    private lateinit var underTest: DeletePhotoInFavouriteInteractor

    @Before
    fun startTest() {
        underTest = DeletePhotoInFavouriteInteractorImpl(photoDatabase)
    }

    @Test
    fun `delete photo in favourtes complete`() {
        every { photoDatabase.deleteInFavourite(any()) } returns Completable.complete()

        underTest.invoke(photo)
            .subscribe(testObserver)
        testObserver.assertResult()

        verify { photoDatabase.deleteInFavourite(photo) }
    }
}