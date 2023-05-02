package com.wla.petfeeder.auth.providers

import com.wla.petfeeder.*
import com.wla.petfeeder.auth.VerificationPayload

data class AuthProvider(
    val code: String = "",
    val email: String = ""
)

data class AuthProviderCanHandle(
    val confirmCode: suspend (VerificationPayload) -> Unit = {}
) : CanHandle

typealias AuthProviderUnit = Unidad<AuthProvider, Dependencies, AuthProviderCanHandle>

fun authProviderUnit(): AuthProviderUnit{
    val unit: AuthProviderUnit = Unidad(initialState = AuthProvider(), canHandle = AuthProviderCanHandle())
    unit.canHandle {
        it.handle.copy(
            confirmCode = {

            }
        )
    }
    return unit
}