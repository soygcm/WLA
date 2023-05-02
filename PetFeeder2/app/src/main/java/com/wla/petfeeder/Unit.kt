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

interface Dependencies
class NoDependencies : Dependencies

interface CanHandle
class CanHandleNothing : CanHandle

class DoNothing(
    override val name: String = "Do nothing",
    override val payload: Any = 0
) : Action<Any>

typealias Then<T> = (then: (T) -> T) -> Unit
data class WhenActionThen<T, D : Dependencies>(
    val action: Action<*>,
//    val state: T,
    private val dependencies: D,
    val then: Then<T>
) {
    val dependency: D
        get() = dependencies
}

typealias WhenThen<T, D> = (WhenActionThen<T, D>) -> Unit

class Unidad<T, D : Dependencies, E : CanHandle>(
    private var _state: T,
    val action: Action<Any> = DoNothing(),
    private val dependencies: D = NoDependencies() as D,
//    private val dependencies: Dependencies = Dependencies(dependencies = listOf()),
    private val config: Any = 0
) {

    val state: T
        get() = _state

    private var _canHandle: E = CanHandleNothing() as E

    private var _useCases: MutableList<WhenThen<T, D>> = mutableListOf()
    fun whenAction(action: Action<*>) {
        _useCases.forEach { whenThen ->
            whenThen(
                WhenActionThen(action = action, dependencies = dependencies, then = { then ->
                    _state = then(state)
                })
            )
        }
    }
    fun whenActionThen(whenThen: WhenThen<T, D>) {
        _useCases.add(whenThen)
    }

    val handle: E
        get() = _canHandle
    fun canHandle(canHandle: () -> E) {
        _canHandle = canHandle()
    }

    val dependOn: D
        get() = dependencies
}
