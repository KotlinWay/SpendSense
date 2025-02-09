package info.javaway.spend_sense.extensions

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

inline fun <State> MutableStateFlow<State>.updateState(block: State.() -> State) =
    this.update { block(it) }