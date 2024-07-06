package com.example.model.state

import com.example.model.User

sealed class UserDetailsUiState {
    data class Success(val user: User) : UserDetailsUiState()
    data class Error(val error: Throwable) : UserDetailsUiState() {
        fun getErrorMessage(): String {
            return error.message ?: "Unknown error"
        }
    }
    object NotFound : UserDetailsUiState() {
        fun getErrorMessage(): String {
            return "User not found"
        }
    }
    object Empty : UserDetailsUiState()
}