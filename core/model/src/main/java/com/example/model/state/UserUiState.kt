package com.example.model.state

import com.example.model.User

sealed class UserUiState {
    data class Success(val users: List<User>) : UserUiState()
    data class Error(val error: Throwable) : UserUiState()
    object Empty : UserUiState()
}
