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

typealias Then<T> = (then: (T) -> T) -> Unit
data class WhenActionThen<T>(val action: Action<*>, val then: Then<T>)

typealias WhenThen<T> = (WhenActionThen<T>) -> Unit

class Unidad<T>(
    private var _state: T,
    val action: Action<Any> = DoNothing(),
    private val dependencies: Dependencies = Dependencies(dependencies = listOf()),
    private val config: Any = 0
) {

    val state: T
        get() = _state

    private var _useCases: MutableList<WhenThen<T>> = mutableListOf()
    fun whenAction(action: Action<*>) {
        _useCases.forEach { whenThen ->
            whenThen(
                WhenActionThen(action = action, then = { then ->
                    _state = then(state)
                })
            )
        }
    }
    fun whenActionThen(whenThen: WhenThen<T>) {
        _useCases.add(whenThen)
    }
    fun handle() {}
}
