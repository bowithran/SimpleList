package com.example.github

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.UserUseCase
import com.example.model.User
import com.example.model.state.UserDetailsUiState
import com.example.model.state.UserEventsUiState
import com.example.model.state.UserUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel@Inject constructor(
    private val userUseCase: UserUseCase,
): ViewModel() {
    private val _userListUiState = mutableStateOf<UserUiState>(UserUiState.Empty)
    val userUiState: State<UserUiState>
        get() = _userListUiState

    private val _userDetailsUiState = mutableStateOf<UserDetailsUiState>(UserDetailsUiState.Empty)
    val userDetailsUiState: State<UserDetailsUiState>
        get() = _userDetailsUiState

    private val _userEventsUiState = mutableStateOf<UserEventsUiState>(UserEventsUiState.Empty)
    val userEventsUiState: State<UserEventsUiState>
        get() = _userEventsUiState

    fun getUsers(completion: () -> Unit) {
        viewModelScope.launch {
            try {
                _userListUiState.value = userUseCase.getUserListState()
            } catch (e: Exception) {
                Log.e("UserViewModel", "getUsers: ", e)
                _userListUiState.value = UserUiState.Error(e)
            }.also {
                completion()
            }
        }
    }

    fun getUserById(userId: Int) {
        viewModelScope.launch {
            try {
                _userDetailsUiState.value = userUseCase.getUserById(userId)
            } catch (e: Exception) {
                Log.e("UserViewModel", "getUserById: ", e)
                _userDetailsUiState.value = UserDetailsUiState.Error(e)
            }
        }
    }

    fun getUserEvents(login: String, completion: () -> Unit) {
        viewModelScope.launch {
            try {
                _userEventsUiState.value = userUseCase.getUserEvents(login)
            } catch (e: Exception) {
                Log.e("UserViewModel", "getUserEvents: ", e)
                _userEventsUiState.value = UserEventsUiState.Error(e)
            }.also {
                completion()
            }
        }
    }
}