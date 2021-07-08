package com.pavellukyanov.themartian.base

import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.reactivex.observers.TestObserver
import org.junit.After

open class BaseTest<T : Any?> {

    internal val testObserver = TestObserver<T>()

    @After
    open fun endTest() {
        testObserver.assertSubscribed()
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        confirmVerified()
        clearAllMocks()
        testObserver.dispose()
    }
}