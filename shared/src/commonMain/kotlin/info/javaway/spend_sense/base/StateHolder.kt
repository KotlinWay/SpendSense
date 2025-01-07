package info.javaway.spend_sense.base

import kotlinx.coroutines.flow.StateFlow

interface StateHolder<State> {
    val state: StateFlow<State>
}