package com.wla.petfeeder

interface Action {
//    val name: String
//    val payload: T
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
//    override val name: String = "Do nothing",
//    override val payload: Any = 0
) : Action

typealias Then<T> = (then: (T) -> T) -> Unit
data class WhenActionThen<T, D : Dependencies>(
    val action: Action,
//    val state: T,
    private val dependencies: D,
    val then: Then<T>
) {
    val dependency: D
        get() = dependencies
}

typealias WhenThen<T, D, E> = (Unidad<T, D, E>) -> Unit

data class Unidad<T, D : Dependencies, E : CanHandle>(
    private var initialState: T,
    val action: Action = DoNothing(),
    private val dependencies: D = NoDependencies() as D,
    private var canHandle: E = CanHandleNothing() as E,
//    private val dependencies: Dependencies = Dependencies(dependencies = listOf()),
    private val config: Any = 0,
    val then: Then<T> = {}
) {

    val state: T
        get() = initialState

//    private var _canHandle: E = CanHandleNothing() as E

    private var _useCases: MutableList<WhenThen<T, D, E>> = mutableListOf()
    fun handleAction(action: Action) {
        _useCases.forEach { whenThen ->
            whenThen(
                this.copy(action = action, then = { then ->
                    initialState = then(state)
                })
            )
        }
    }
    fun whenActionThen(whenThen: WhenThen<T, D, E>) {
        _useCases.add(whenThen)
    }

    fun <oT, oD : Dependencies, oE : CanHandle> contains(other: Unidad<oT, oD, oE>, merge: (T, oT) -> T) {
        other.whenActionThen {
            this.handleAction(it.action)
            initialState = merge(state, it.state)
        }
    }

    val handle: E
        get() = canHandle
    fun canHandle(canHandle: (Unidad<T, D, E>) -> E) {
        this.canHandle = canHandle(this)
    }

    val dependOn: D
        get() = dependencies
}
