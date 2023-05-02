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
    val code: String = "",
    val email: String = ""
)

data class AuthFlow(
    val auth: Auth = Auth(),
    val screen: String = ""
)

class VerificationSuccess(
    override val name: String = "Verification success",
    override val payload: kotlin.Unit = Unit
) : Action<kotlin.Unit>

fun whenVerificationSuccessThenUserShallSeeLoginScreen(actionThen: WhenActionThen<AuthFlow, NoDependencies>) {
    if (actionThen.action is VerificationSuccess) {
        actionThen.then {
            it.copy(screen = "LoginScreen")
        }
    }
}

val authFlowUnitInitialState = Unidad(_state = AuthFlow(), dependencies = NoDependencies())
fun authFlowUnit(
    unit: Unidad<AuthFlow, NoDependencies> = authFlowUnitInitialState
): Unidad<AuthFlow, NoDependencies> {
    unit.whenActionThen { actionThen ->
        whenVerificationSuccessThenUserShallSeeLoginScreen(actionThen)
    }
    return unit
}
