package com.example.datastore

import com.example.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataStore {
    val savedUsers: Flow<SavedUsers>
    suspend fun saveUser(user: User)
    suspend fun saveUserList(users: List<User>)
    suspend fun getUserById(id: Int): User?
}