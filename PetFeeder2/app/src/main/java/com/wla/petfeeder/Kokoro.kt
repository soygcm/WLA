package com.wla.petfeeder


interface Action<T> {
    val name: String
    val payload: T
}

enum class DependencyType {
    DATA_SOURCE, PROVIDER, ADAPTER,
}

interface Dependency<Req, Res> {
    val type: DependencyType
    fun will(req: Req): Res
}

data class Dependencies(
    val dependencies: List<Dependency<Any, Any>>
)

class DoNothing(
    override val name: String = "Do nothing",
    override val payload: Any = 0
) : Action<Any>

data class Kokoro(
    val currentState: AuthFlow = AuthFlow(),
    val action: Action<Any> = DoNothing(),
    val dependencies: Dependencies = Dependencies(dependencies = listOf()),
    val config: Any = 0
)