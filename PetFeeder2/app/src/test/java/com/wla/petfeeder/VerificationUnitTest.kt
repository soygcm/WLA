package com.wla.petfeeder

import com.wla.petfeeder.auth.verificationUnit
import kotlinx.coroutines.runBlocking
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

    @Test
    fun test_shallSendConfirmationCodeToSignUpAdapter_whenOnClickConfirmIsCalled() = runBlocking {
        // Given
//        val provider = MockAuth()
        val sut = verificationUnit(
            Verification(
                code = "1234",
                email = "email@nextern.com"
            )
        )

        // When
        sut.whenAction(OnClickConfirm())
//
//        // Then
//        assertEquals("1234", provider.confirmationCode)
//        assertEquals("email@nextern.com", provider.email)
    }
}
