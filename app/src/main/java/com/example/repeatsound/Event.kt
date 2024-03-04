package com.example.exercise03

import com.example.repeatsound.State

sealed class Event {
    data object StartGame : Event()
    data object Restart : Event()
    data class ButtonClicked(val number: Int) : Event()
    data class ChangeRecord(val record: Int) : Event()
    data class ChangeMode(var mode: State.GameMode) : Event()
}