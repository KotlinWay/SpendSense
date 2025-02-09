package info.javaway.spend_sense.base

interface UiEventHandler<UiEvent> {
    fun onEvent(event: UiEvent)
}