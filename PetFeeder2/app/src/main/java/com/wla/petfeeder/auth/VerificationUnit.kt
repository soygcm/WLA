package com.wla.petfeeder.auth

import com.wla.petfeeder.Unidad
import com.wla.petfeeder.Verification

fun verificationUnit(initialState: Verification = Verification()): Unidad<Verification> {

    return Unidad(_state = initialState)
}
