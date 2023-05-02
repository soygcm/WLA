package com.wla.petfeeder

import com.wla.petfeeder.auth.Status
import com.wla.petfeeder.auth.VerificationSuccess
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AuthFlowUnitTest {

    @Test
    fun test_UserShallSeeLoginScreen_WhenVerificationSuccess2() {
        // Given
        val sut = authFlowUnit()

        // When
        sut.handleAction(VerificationSuccess())

        // Then
        assertEquals(sut.state.screen, "LoginScreen")
    }

    @Test
    fun test_AuthFlowContainsVerificationLogic() = runBlocking  {
        // Given
        val sut = authFlowUnit()

        // When
        val verification = sut.handle.verification
        verification.clickConfirmCode()

        // Then
        assertEquals(sut.state.auth.verification.status, Status.Success)
    }
}
