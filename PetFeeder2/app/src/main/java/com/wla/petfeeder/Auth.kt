package com.wla.petfeeder

data class User(
    val name: String = "",
    val age: Int = 0,
    var email: String = ""
)

data class Auth(
    val user: User = User(),
    val verification: Verification = Verification()

)

data class Verification(
    var success: () -> Unit = {}
)

data class AuthFlow(
    val auth: Auth = Auth(),
    var screen: String = ""
)

fun authFlowUnit(some: Kokoro = Kokoro()): AuthFlow {
    var authFlow = AuthFlow()
    authFlow.auth.verification.success = {
        authFlow.screen = "LoginScreen"
    }
    return authFlow
}

fun authFlowUnit2(some: Kokoro = Kokoro()): Kokoro {
    var authFlow = AuthFlow()
    authFlow.auth.verification.success = {
        authFlow.screen = "LoginScreen"
    }
    return some
}
