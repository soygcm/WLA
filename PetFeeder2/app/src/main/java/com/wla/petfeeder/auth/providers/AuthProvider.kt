package com.wla.petfeeder.auth.providers

import com.wla.petfeeder.*
import com.wla.petfeeder.whenVerificationSuccessThenUserShallSeeLoginScreen

data class AuthProvider(
    val code: String = "",
    val email: String = ""
)

fun mockAuthProvider(unit: Unidad<AuthProvider, Dependencies> = Unidad(_state = AuthProvider())): Unidad<AuthProvider, Dependencies> {

    return unit
}