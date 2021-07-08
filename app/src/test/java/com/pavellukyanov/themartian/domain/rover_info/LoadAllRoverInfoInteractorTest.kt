package com.pavellukyanov.themartian.domain.rover_info

import com.pavellukyanov.themartian.base.BaseTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class LoadAllRoverInfoInteractorTest : BaseTest<List<RoverInfo>>() {
    private val roverInfoRepo = mockk<RoverInfoRepo>()
    private val listRoverInfo = emptyList<RoverInfo>()

    private lateinit var underTest: LoadAllRoverInfoInteractor

    @Before
    fun startTest() {
        underTest = LoadAllRoverInfoInteractorImpl(roverInfoRepo)
    }

    @Test
    fun `response complete`() {
        every { roverInfoRepo.loadAllRoverInfo() } returns Single.just(listRoverInfo)

        underTest.invoke()
            .subscribe(testObserver)
        testObserver.assertValue(listRoverInfo)

        verify { roverInfoRepo.loadAllRoverInfo() }
    }
}