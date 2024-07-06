package com.example.domain.usecase.impl

import android.util.Log
import com.example.data.repository.UserRepository
import com.example.domain.usecase.UserUseCase
import com.example.model.state.UserDetailsUiState
import com.example.model.state.UserEventsUiState
import com.example.model.state.UserUiState
import javax.inject.Inject
import kotlin.math.log

class UserUseCaseImpl@Inject constructor(
    private val userRepository: UserRepository
) : UserUseCase {
    override suspend fun getUserListState(): UserUiState {
        return userRepository.getUserListFromApi()
            .fold(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        UserUiState.Success(it)
                    } else {
                        UserUiState.Empty
                    }
                },
                onFailure = {
                    Log.e("UserUseCaseImpl", "getUserListState: $it")
                    UserUiState.Error(it)
                }
            )
    }

    override suspend fun getUserById(id: Int): UserDetailsUiState {
        return userRepository.getUserById(id)
            .fold(
                onSuccess = {
                    if (it != null) {
                        UserDetailsUiState.Success(it)
                    } else {
                        UserDetailsUiState.NotFound
                    }
                },
                onFailure = {
                    Log.e("UserUseCaseImpl", "getUserById: $it")
                    UserDetailsUiState.Error(it)
                }
            )
    }

    override suspend fun getUserEvents(login: String): UserEventsUiState {
        return userRepository.getUserEvents(login)
            .fold(
                onSuccess = {
                    if (it != null) {
                        UserEventsUiState.Success(it)
                    } else {
                        UserEventsUiState.NotFound
                    }
                },
                onFailure = {
                    UserEventsUiState.Error(it)
                }
            )
    }
}