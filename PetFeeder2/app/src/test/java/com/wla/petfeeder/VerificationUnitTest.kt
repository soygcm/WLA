package com.wla.petfeeder

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class VerificationUnitTest {

    @Test
    fun test_UserShallSeeLoginScreen_WhenVerificationSuccess2() {
        // Given
        val sut = authFlowUnit()

        // When
        sut.whenAction(VerificationSuccess())

        // Then
        assertEquals(sut.state.screen, "LoginScreen")
    }
}
