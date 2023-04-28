package com.wla.petfeeder

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AuthFlowUnitTest {
//    @Test
//    fun test_UserShallSeeLoginScreen_WhenVerificationSuccess() {
//        // Given
//        val sut = authFlowUnit()
//
//        // When
//        sut.auth.verification.success()
//
//        // Then
//        assertEquals(sut.screen, "LoginScreen")
//    }

    @Test
    fun test_UserShallSeeLoginScreen_WhenVerificationSuccess2() {
        // Given
        val sut = authFlowUnit2()

        // When
        sut.whenAction(VerificationSuccess())

        // Then
        assertEquals(sut.state.screen, "LoginScreen")
    }
}
