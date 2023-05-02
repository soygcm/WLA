package com.wla.petfeeder

import com.wla.petfeeder.auth.Verification
import com.wla.petfeeder.auth.VerificationHandle
import com.wla.petfeeder.auth.VerificationSuccess
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.dependencies.RealAuthDependencies
import com.wla.petfeeder.auth.verificationUnit

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

data class AuthFlowCanHandle(
    val verification: VerificationHandle = VerificationHandle()
):CanHandle

fun whenVerificationSuccessThenUserShallSeeLoginScreen(unit: AuthFlowUnit) {
    if (unit.action is VerificationSuccess) {
        unit.then {
            it.copy(screen = "LoginScreen")
        }
    }
}

typealias AuthFlowUnit = Unidad<AuthFlow, AuthDependencies, AuthFlowCanHandle>

val authFlowUnitInitialState: AuthFlowUnit = Unidad(_state = AuthFlow(), canHandle = AuthFlowCanHandle(), dependencies = RealAuthDependencies())
fun authFlowUnit(
    unit: AuthFlowUnit = authFlowUnitInitialState
): AuthFlowUnit {
    val verificationUnit = verificationUnit()

    unit.whenActionThen { it ->
        whenVerificationSuccessThenUserShallSeeLoginScreen(it)
    }
    unit.canHandle { canHandle->
        canHandle.copy(
            verification = verificationUnit.handle
        )
    }
    unit.contains(verificationUnit){ state, verificationState ->
        state.copy(auth = state.auth.copy(verification = verificationState))
    }

//    val verification = unit.handle.verification
//    verification.clickConfirmCode()
    return unit
}
