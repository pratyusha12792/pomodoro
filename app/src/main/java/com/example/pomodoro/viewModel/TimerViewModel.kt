package com.example.pomodoro.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TimerViewModel : ViewModel() {

    private val _timeLeft = MutableStateFlow(25 * 60) // in seconds
    val timeLeft: StateFlow<Int> = _timeLeft

    private val _isRunning = MutableStateFlow(false)
    val isRunning: StateFlow<Boolean> = _isRunning

    private val _roundsCompleted = MutableStateFlow(0)
    val roundsCompleted: StateFlow<Int> = _roundsCompleted

    private var totalTime = 25 * 60

    fun toggleTimer() {
        _isRunning.value = !_isRunning.value
        if (_isRunning.value) {
            startTimer()
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (_isRunning.value && _timeLeft.value > 0) {
                delay(1000)
                _timeLeft.value -= 1
            }
            if (_timeLeft.value == 0) {
                _isRunning.value = false
                _roundsCompleted.value += 1
            }
        }
    }

    fun resetTimer() {
        _isRunning.value = false
        _timeLeft.value = totalTime
    }

    fun stopTimer() {
        _isRunning.value = false
        _timeLeft.value = 0
    }

    fun updateTimer(newSeconds: Int) {
        totalTime = newSeconds
        _timeLeft.value = newSeconds
    }
}
