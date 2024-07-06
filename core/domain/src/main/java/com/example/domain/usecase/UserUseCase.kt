package com.example.domain.usecase

import com.example.model.state.UserDetailsUiState
import com.example.model.state.UserEventsUiState
import com.example.model.state.UserUiState

interface UserUseCase {
    suspend fun getUserListState(): UserUiState
    suspend fun getUserById(id: Int): UserDetailsUiState
    suspend fun getUserEvents(login: String): UserEventsUiState
}