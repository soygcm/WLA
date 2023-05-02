package com.wla.petfeeder.auth

import com.wla.petfeeder.*
import com.wla.petfeeder.auth.dependencies.AuthDependencies
import com.wla.petfeeder.auth.dependencies.RealAuthDependencies
import com.wla.petfeeder.auth.providers.AuthProvider

class VerificationSuccess(
    override val name: String = "Verification success",
    override val payload: Unit = Unit
) : Action<Unit>

data class VerificationPayload(
    val code: String = "",
    val email: String = ""
){
    constructor(verification: Verification): this(
        code = verification.code,
        email = verification.email)
}

enum class Status{
    Initial, Loading, Success, Error
}

data class VerificationHandle(
    val clickConfirmCode: suspend () -> Unit = {}
): CanHandle

data class Verification(
    val code: String = "",
    val email: String = "",
    val status: Status = Status.Initial,
)

suspend fun whenOnClickConfirmShallSendConfirmationCodeToSignUpAdapter(unit: VerificationUnit) {
    val authProvider = unit.dependOn.authProvider()
    authProvider.handle.confirmCode(VerificationPayload(unit.state))
}

fun shallHandleWhenOnClickConfirm(canHandle: VerificationHandle, unit: VerificationUnit): VerificationHandle {
    return canHandle.copy(
        clickConfirmCode = {
            whenOnClickConfirmShallSendConfirmationCodeToSignUpAdapter(unit)
            unit.whenAction(VerificationSuccess())
        }
    )
}

fun whenVerificationSuccessShallChangeStatusToSuccess(unit: VerificationUnit){
    if(unit.action is VerificationSuccess){
        unit.then{
            it.copy(status = Status.Success)
        }
    }
}

typealias VerificationUnit = Unidad<Verification, AuthDependencies, VerificationHandle>

val initialVerificationUnit:VerificationUnit = Unidad(_state = Verification(), dependencies = RealAuthDependencies(), canHandle = VerificationHandle())

fun verificationUnit(unit: VerificationUnit = initialVerificationUnit):VerificationUnit {
    unit.whenActionThen {
        whenVerificationSuccessShallChangeStatusToSuccess(it)
    }
    unit.canHandle {
        shallHandleWhenOnClickConfirm(it, unit)
    }
    return unit
}
