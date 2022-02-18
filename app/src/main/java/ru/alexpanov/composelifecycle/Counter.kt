package ru.alexpanov.composelifecycle

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class Counter {
    private val _state = MutableStateFlow(0)
    val state = _state.asStateFlow()

    fun increment() {
        _state.value = state.value + 1
    }

    fun dispose() {
        // clean-up resources
    }
}