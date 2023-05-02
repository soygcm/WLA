package com.wla.petfeeder

import com.wla.petfeeder.auth.Verification

data class User(
    val name: String = "",
    val age: Int = 0,
    var email: String = ""
)

data class Auth(
    val user: User = User(),
    val verification: Verification = Verification()

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

typealias AuthFlowUnit = Unidad<AuthFlow, NoDependencies, CanHandleNothing>

val authFlowUnitInitialState:AuthFlowUnit = Unidad(_state = AuthFlow())
fun authFlowUnit(
    unit: AuthFlowUnit = authFlowUnitInitialState
): AuthFlowUnit {
    unit.whenActionThen { actionThen ->
        whenVerificationSuccessThenUserShallSeeLoginScreen(actionThen)
    }
    unit.handle
    return unit
}
