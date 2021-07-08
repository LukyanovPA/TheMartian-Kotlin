package com.pavellukyanov.themartian.photo

import com.pavellukyanov.themartian.base.BaseTest
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractor
import com.pavellukyanov.themartian.domain.photo.LoadPhotoForEarthDateInteractorImpl
import com.pavellukyanov.themartian.domain.photo.Photo
import com.pavellukyanov.themartian.domain.photo.PhotoRepo
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LoadPhotoForEarthDateInteractorTest : BaseTest<List<Photo>>() {
    private val photoRepo = mockk<PhotoRepo>()
    private val listPhoto = emptyList<Photo>()
    private val roverName = "Spirit"
    private val earthDate = "2021-07-07"

    private lateinit var underTest: LoadPhotoForEarthDateInteractor

    @Before
    fun startTest() {
        underTest = LoadPhotoForEarthDateInteractorImpl(photoRepo)
    }

    @Test
    fun `response complete`() {
        every { photoRepo.loadPhotoForEarthDate(any(), any()) } returns Single.just(listPhoto)

        underTest.invoke(roverName, earthDate)
            .subscribe(testObserver)
        testObserver.assertValue(listPhoto)

        verify { photoRepo.loadPhotoForEarthDate(roverName, earthDate) }
    }
}