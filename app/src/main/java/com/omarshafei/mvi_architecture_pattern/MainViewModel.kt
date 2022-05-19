package com.omarshafei.mvi_architecture_pattern

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {

    val intentChannel = Channel<MainIntent>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow<MainViewState>(MainViewState.Idle)
    val viewSate = _viewState
    private var number = 0

    init {
        processAddNumberIntent()
    }

//    process
    private fun processAddNumberIntent() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect {
                when(it) {
                    MainIntent.addNumberIntent -> reduceAddNumberResult()
                }
            }
        }
    }
//    reduce
    private fun reduceAddNumberResult() {
        viewModelScope.launch {
            _viewState.value =
                try {
                    MainViewState.Number(number = number++)
                }catch (e: Exception) {
                    MainViewState.Error(e.message!!)
                }
        }
    }
}