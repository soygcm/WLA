package com.wla.petfeeder.auth.dependencies

import com.wla.petfeeder.Dependencies
import com.wla.petfeeder.Unidad
import com.wla.petfeeder.auth.providers.AuthProvider

interface AuthDependencies: Dependencies {
    fun getAuthProvider(): Unidad<AuthProvider, Dependencies>
}

class RealAuthDependencies: AuthDependencies {
    override fun getAuthProvider(): Unidad<AuthProvider, Dependencies>{
        return Unidad(_state = AuthProvider())
    }
}

