package com.omarshafei.mvi_architecture_pattern

sealed class MainViewState {
    // loading
    // data
    // error

    object Idle: MainViewState()
    data class Number(val number: Int): MainViewState()
    data class Error(val error: String): MainViewState()
}
