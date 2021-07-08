package com.pavellukyanov.themartian.domain.rover_info

import com.pavellukyanov.themartian.base.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class GetRoverInfoInteractorTest : BaseTest<RoverInfo>() {
    private val roverInfoRepo = mockk<RoverInfoRepo>()
    private val roverInfo = mockk<RoverInfo>()
    private val roverName = "Sprirt"

    private lateinit var underTest: GetRoverInfoInteractor

    @Before
    fun startTest() {
        underTest = GetRoverInfoInteractorImpl(roverInfoRepo)
    }

    @Test
    fun `response complete`() {
        every { roverInfoRepo.getRoverInfo(any()) } returns Single.just(roverInfo)

        underTest.invoke(roverName)
            .subscribe(testObserver)
        testObserver.assertValue(roverInfo)

        verify { roverInfoRepo.getRoverInfo(roverName) }
    }
}