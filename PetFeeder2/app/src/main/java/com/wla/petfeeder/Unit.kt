package com.wla.petfeeder

interface Action

interface Dependencies
class NoDependencies : Dependencies

interface CanHandle
class CanHandleNothing : CanHandle

class DoNothing : Action

typealias Then<T> = (then: (T) -> T) -> Unit

typealias WhenThen<T, D, E> = (Unidad<T, D, E>) -> Unit

@Suppress("UNCHECKED_CAST")
data class Unidad<T, D : Dependencies, E : CanHandle>(
    private var initialState: T,
    val action: Action = DoNothing(),
    private val dependencies: D = (NoDependencies() as? D) ?: throw Error("You need to specify what are your dependencies when you create the unit"),
    private var canHandle: E = (CanHandleNothing() as? E) ?: throw Error("You need to specify what the unit can handle when you create the unit"),
    private val config: Any = 0,
    val then: Then<T> = {}
) {

    val state: T
        get() = initialState

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
