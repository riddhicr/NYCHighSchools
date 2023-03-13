package com.example.nychighschools.utils


/**
 * [Sealed class] - Defines the state of the UI
 */

sealed class UiState<out T>{

    object LOADING: UiState<Nothing>() //loading response
    data class SUCCESS<T>(val response: T): UiState<T>() //response succeeded
    data class ERROR(val error: Exception): UiState<Nothing>() //error in response

}