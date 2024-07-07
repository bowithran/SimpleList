package com.example.data.repository.impl

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.repository.UserRepository
import com.example.datastore.UserDataStore
import com.example.model.User
import com.example.model.UserEvent
import com.example.network.retrofit.GithubApis
import javax.inject.Inject

class
UserRepositoryImpl@Inject
    constructor(
        private val api: GithubApis,
        private val dataStore: UserDataStore,
    ) : UserRepository {
        override suspend fun getUserListFromApi(): Result<List<User>> =
            runCatching {
                api.getUsers(token = BuildConfig.GITHUB_TOKEN)
            }.onSuccess {
                if (it.isNotEmpty()) {
                    saveUserList(it)
                }
                Result.success(it)
            }.onFailure {
                // Handle error
                Log.e("UserRepositoryImpl", "getUserListFromApi: $it")
                Result.failure<Throwable>(it)
            }

        override suspend fun getUserById(id: Int): Result<User?> =
            runCatching {
                dataStore.getUserById(id)
            }.onSuccess {
                it?.let {
                    Result.success(it)
                } ?: run {
                    Result.failure<Throwable>(Exception("User not found"))
                }
            }.onFailure {
                // Handle error
                Log.e("UserRepositoryImpl", "getUserById: $it")
                Result.failure<Throwable>(it)
            }

        override suspend fun saveUser(user: User) {
            try {
                dataStore.saveUser(user)
            } catch (e: Exception) {
                // Handle error
                Log.e("UserRepositoryImpl", "saveUser: $e")
                throw Exception("Failed to save user")
            }
        }

        override suspend fun saveUserList(users: List<User>) {
            try {
                dataStore.saveUserList(users)
            } catch (e: Exception) {
                // Handle error
                Log.e("UserRepositoryImpl", "saveUserList: $e")
                throw Exception("Failed to save user list")
            }
        }

        override suspend fun getUserEvents(login: String): Result<List<UserEvent>?> =
            runCatching {
                api.getUserEvents(login)
            }.onSuccess {
                Result.success(it)
            }.onFailure {
                // Handle error
                Log.e("UserRepositoryImpl", "getUserEvents: $it")
                Result.failure<Throwable>(it)
            }
    }
