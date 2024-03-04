package com.example.repeatsound

data class State(
    var level: Int = 1,
    var record: Int = 0,
    var doButtonsEnabled: Boolean = false,
    var reButtonsEnabled: Boolean = false,
    var miButtonsEnabled: Boolean = false,
    var faButtonsEnabled: Boolean = false,
    var wrongSound: Boolean = false,
    var freeGame: Boolean = false
) {
    enum class GameMode {
        GAME, FREE
    }
}