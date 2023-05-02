package com.wla.petfeeder

import com.wla.petfeeder.auth.OnClickConfirmCode
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.providers.AuthProvider
import com.wla.petfeeder.auth.providers.mockAuthProvider
import com.wla.petfeeder.auth.verificationUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MockAuthDependencies: AuthDependencies {
    override fun getAuthProvider(): Unidad<AuthProvider, Dependencies>{
        return Unidad(_state = AuthProvider())
    }
}

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class VerificationUnitTest {

    @Test
    fun test_shallSendConfirmationCodeToSignUpAdapter_whenOnClickConfirmIsCalled() = runBlocking {
        // Given
        val provider = mockAuthProvider()
        val unit = Unidad(
            _state = Verification(
                code = "1234",
                email = "email@nextern.com"
            ),
            dependencies = MockAuthDependencies() as AuthDependencies
        )
        val sut = verificationUnit(unit)

        // When
        sut.whenAction(OnClickConfirmCode())
//
//        // Then
        assertEquals("1234", provider.state.code)
        assertEquals("email@nextern.com", provider.state.email)
    }
}
