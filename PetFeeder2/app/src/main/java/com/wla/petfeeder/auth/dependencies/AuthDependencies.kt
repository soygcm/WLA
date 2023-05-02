package com.wla.petfeeder.auth.dependencies

import com.wla.petfeeder.CanHandle
import com.wla.petfeeder.Dependencies
import com.wla.petfeeder.Unidad
import com.wla.petfeeder.auth.VerificationPayload
import com.wla.petfeeder.auth.providers.AuthProvider
import com.wla.petfeeder.auth.providers.AuthProviderUnit


interface AuthDependencies : Dependencies {
    fun authProvider(): AuthProviderUnit
}

class RealAuthDependencies : AuthDependencies {
    override fun authProvider(): AuthProviderUnit {
        return Unidad(_state = AuthProvider())
    }
}
