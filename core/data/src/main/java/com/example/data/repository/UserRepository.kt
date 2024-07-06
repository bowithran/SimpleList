package com.example.data.repository

import com.example.model.User
import com.example.model.UserEvent

interface UserRepository {
    suspend fun getUserListFromApi() : Result<List<User>>
    suspend fun getUserById(id: Int) : Result<User?>
    suspend fun saveUser(user: User)
    suspend fun saveUserList(users: List<User>)
    suspend fun getUserEvents(login: String): Result<List<UserEvent>?>
}