package com.wla.petfeeder.auth

import com.wla.petfeeder.*
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.dependencies.RealAuthDependencies

class VerificationSuccess() : Action

class VerificationStart() : Action

data class VerificationPayload(
    val code: String = "",
    val email: String = ""
) {
    constructor(verification: VerificationState) : this(
        code = verification.code,
        email = verification.email
    )
}

enum class Status {
    Initial, Loading, Success, Error
}

data class VerificationCanHandle(
    val clickConfirmCode: suspend () -> Unit = {}
) : CanHandle

data class VerificationState(
    val code: String = "",
    val email: String = "",
    val status: Status = Status.Initial
)

suspend fun whenOnClickConfirmShallSendConfirmationCodeToSignUpAdapter(unit: VerificationUnit) {
    val authProvider = unit.dependOn.authProvider()
    val handle = authProvider.handle
    handle.confirmCode(VerificationPayload(unit.state))
}

fun shallHandleWhenOnClickConfirm(unit: VerificationUnit): VerificationCanHandle {
    return unit.handle.copy(
        clickConfirmCode = {
            unit.handleAction(VerificationStart())
            whenOnClickConfirmShallSendConfirmationCodeToSignUpAdapter(unit)
            unit.handleAction(VerificationSuccess())
        }
    )
}

fun whenVerificationSuccessShallChangeStatusToSuccess(unit: VerificationUnit) {
    if (unit.action is VerificationSuccess) {
        unit.then {
            it.copy(status = Status.Success)
        }
    }
}

typealias VerificationUnit = Unidad<VerificationState, AuthDependencies, VerificationCanHandle>

val initialVerificationUnit: VerificationUnit = Unidad(initialState = VerificationState(), dependencies = RealAuthDependencies(), canHandle = VerificationCanHandle())

fun verificationUnit(unit: VerificationUnit = initialVerificationUnit): VerificationUnit {
    unit.whenActionThen {
        whenVerificationSuccessShallChangeStatusToSuccess(it)
    }
    unit.canHandle {
        shallHandleWhenOnClickConfirm(it)
    }
    return unit
}
