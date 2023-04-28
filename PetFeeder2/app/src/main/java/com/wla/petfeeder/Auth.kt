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
    val screen: String = ""
)

class VerificationSuccess(
    override val name: String = "Verification success",
    override val payload: Unit = Unit
) : Action<Unit>

fun whenVerificationSuccessThenUserShallSeeLoginScreen(actionThen: WhenActionThen<AuthFlow>) {
    if (actionThen.action is VerificationSuccess) {
        actionThen.then {
            it.copy(screen = "LoginScreen")
        }
    }
}

fun authFlowUnit2(unit: Kokoro<AuthFlow> = Kokoro(initialState = AuthFlow())): Kokoro<AuthFlow> {
    unit.whenActionThen { actionThen ->
        whenVerificationSuccessThenUserShallSeeLoginScreen(actionThen)
    }
    return unit
}
