package com.example.repeatsound

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exercise03.Event
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class ScreenViewModel(private val context: Context) : ViewModel() {
    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()
    private var sequenceSound: MutableList<Int> = mutableListOf()
    private val checkSequence: MutableList<Int> = mutableListOf()
    private val soundsList = listOf(1, 2, 3, 4)

    private fun gameLevel() {
        checkSequence.clear()
        generateLevel()
        viewModelScope.launch {
            sequenceSound.forEach { sounds ->
                setEnable(sounds, true)
                playSound(sounds)
                delay(500)
                setEnable(sounds, false)
            }
            for (sound in 1..4) {
                setEnable(sound, true)
            }
        }
    }

    private fun generateLevel() {
        sequenceSound.add(soundsList.random())
    }

    private fun playSound(buttonNumber: Int) {
        val soundResourceId: Int =
            when (buttonNumber) {
                1 -> R.raw.doo
                2 -> R.raw.re
                3 -> R.raw.mi
                4 -> R.raw.fa
                else -> throw IllegalArgumentException("Invalid button number")
            }
        val mediaPlayer = MediaPlayer.create(context, soundResourceId)
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release()
        }
    }

    private fun onClickButton(sound: Int) {
        if (state.value.freeGame) {
            playSound(sound)
            return
        }
        checkSequence.add(sound)
        if (!checkSound(checkSequence)) {
            _state.update { it.copy(wrongSound = true) }
        } else if (checkSequence.size == sequenceSound.size) {
            Log.d("Qwe", state.value.record.toString())
            val newRecord = maxOf(_state.value.record, _state.value.level)
            _state.update {
                it.copy(
                    level = state.value.level + 1,
                    record = newRecord
                )
            }
            for (el in 1..4) {
                setEnable(el, false)
            }
            gameLevel()
        }
    }

    private fun checkSound(check: MutableList<Int>): Boolean {
        check.forEachIndexed { i, element ->
            if (element != sequenceSound[i]) return false
        }
        return true
    }

    private fun restartGame() {
        _state.update { it.copy(wrongSound = false, level = 1) }
        sequenceSound.clear()
        for (el in 1..4) {
            setEnable(el, false)
        }
        gameLevel()
    }

    private fun setEnable(sound: Int, enable: Boolean) {
        when (sound) {
            1 -> _state.update { it.copy(doButtonsEnabled = enable) }
            2 -> _state.update { it.copy(reButtonsEnabled = enable) }
            3 -> _state.update { it.copy(miButtonsEnabled = enable) }
            4 -> _state.update { it.copy(faButtonsEnabled = enable) }
        }
    }

    private fun changeMode(mode: State.GameMode) {
        if (mode == State.GameMode.FREE) {
            _state.update { it.copy(freeGame = true, wrongSound = false) }
        } else {
            _state.update {
                it.copy(
                    level = 1,
                    doButtonsEnabled = false,
                    reButtonsEnabled = false,
                    miButtonsEnabled = false,
                    faButtonsEnabled = false,
                    freeGame = false,
                    wrongSound = false
                )
            }
            checkSequence.clear()
            sequenceSound.clear()
            gameLevel()
        }
    }

    fun sendEvent(event: Event) {
        when (event) {
            is Event.ButtonClicked -> onClickButton(event.number)
            is Event.ChangeRecord -> _state.update { it.copy(record = event.record) }
            is Event.StartGame -> gameLevel()
            is Event.Restart -> restartGame()
            is Event.ChangeMode -> changeMode(event.mode)
        }
    }
}