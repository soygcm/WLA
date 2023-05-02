package com.wla.petfeeder.auth

import com.wla.petfeeder.*
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.dependencies.RealAuthDependencies
import com.wla.petfeeder.auth.providers.AuthProvider

class OnClickConfirmCode(
    override val name: String = "on Click Confirm",
    override val payload: Unit = Unit
) : Action<Unit>

class AttemptConfirmCode(
    override val name: String = "on Click Confirm",
    override val payload: VerificationPayload
) : Action<VerificationPayload>

fun whenOnClickConfirmIsCalledShallSendConfirmationCodeToSignUpAdapter(actionThen: WhenActionThen<Verification, AuthDependencies>) {
    if (actionThen.action is OnClickConfirmCode) {
        actionThen.then {
            actionThen.dependency.getAuthProvider().whenAction(AttemptConfirmCode(payload = VerificationPayload(it)))
            it
        }
    }
}

typealias VerificationUnit = Unidad<Verification, AuthDependencies>

val initialVerificationUnit:VerificationUnit = Unidad(_state = Verification(), dependencies = RealAuthDependencies())

fun verificationUnit(unit: VerificationUnit = initialVerificationUnit):VerificationUnit {
    unit.whenActionThen { actionThen ->
        whenOnClickConfirmIsCalledShallSendConfirmationCodeToSignUpAdapter(actionThen)
    }
    return unit
}
