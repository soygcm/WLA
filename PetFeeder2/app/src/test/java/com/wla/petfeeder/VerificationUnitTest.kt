package com.wla.petfeeder

import com.wla.petfeeder.auth.*
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.providers.AuthProvider
import com.wla.petfeeder.auth.providers.AuthProviderCanHandle
import com.wla.petfeeder.auth.providers.AuthProviderUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MockAuthDependencies: AuthDependencies {

    lateinit var verificationPayload: VerificationPayload
    override fun authProvider(): AuthProviderUnit{
        val some: AuthProviderUnit = Unidad(initialState = AuthProvider())
        some.canHandle {
            AuthProviderCanHandle(
                confirmCode = {
                    verificationPayload = it
                }
            )
        }
        return some
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class VerificationUnitTest {

    @Test
    fun test_shallSendConfirmationCodeToSignUpAdapter_whenOnClickConfirm() = runBlocking {
        // Given
        val dependencies = MockAuthDependencies()
        val unit: VerificationUnit = initialVerificationUnit.copy(
            initialState = VerificationState(
                code = "1234",
                email = "email@nextern.com"
            ),
            dependencies = dependencies
        )
        val sut = verificationUnit(unit)

        // When
        sut.handle.clickConfirmCode()

        // Then
        assertEquals("1234", dependencies.verificationPayload.code)
        assertEquals("email@nextern.com", dependencies.verificationPayload.email)
    }

    @Test
    fun test_shallChangeStatusToSuccess_whenVerificationSuccess() = runBlocking {
        // Given
        val sut = verificationUnit()
        // When
        sut.handleAction(VerificationSuccess())
        // Then
        assertEquals(Status.Success, sut.state.status)
    }
}
