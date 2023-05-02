package com.wla.petfeeder

import com.wla.petfeeder.auth.AttemptConfirmCode
import com.wla.petfeeder.auth.OnClickConfirmCode
import com.wla.petfeeder.auth.VerificationUnit
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.providers.AuthProvider
import com.wla.petfeeder.auth.providers.mockAuthProvider
import com.wla.petfeeder.auth.verificationUnit
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class MockAuthDependencies: AuthDependencies {

    lateinit var action: Action<*>
    override fun getAuthProvider(): Unidad<AuthProvider, Dependencies>{
        val some: Unidad<AuthProvider, Dependencies> = Unidad(_state = AuthProvider(), dependencies = NoDependencies())
        some.whenActionThen {
            action = it.action
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
    fun test_shallSendConfirmationCodeToSignUpAdapter_whenOnClickConfirmIsCalled() = runBlocking {
        // Given
        val dependencies = MockAuthDependencies()
        val unit: VerificationUnit = Unidad(
            _state = Verification(
                code = "1234",
                email = "email@nextern.com"
            ),
            dependencies = dependencies
        )
        val sut = verificationUnit(unit)

        // When
        sut.whenAction(OnClickConfirmCode())
//
//        // Then
        assertEquals("1234", (dependencies.action as AttemptConfirmCode).payload.code)
        assertEquals("email@nextern.com", (dependencies.action as AttemptConfirmCode).payload.email)
    }
}
