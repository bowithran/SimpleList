package com.example.model.state

import com.example.model.UserEvent

sealed class UserEventsUiState {
    data class Success(val user: List<UserEvent>) : UserEventsUiState()
    data class Error(val error: Throwable) : UserEventsUiState() {
        fun getErrorMessage(): String {
            return error.message ?: "Unknown error"
        }
    }
    object NotFound : UserEventsUiState() {
        fun getErrorMessage(): String {
            return "User not found"
        }
    }
    object Empty : UserEventsUiState()
}