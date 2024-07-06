package com.example.datastore.impl

import androidx.datastore.core.DataStore
import com.example.datastore.SavedUsers
import com.example.datastore.User
import com.example.datastore.UserDataStore
import com.example.datastore.UserSerializer.defaultValue
import com.example.datastore.ext.toDataStoreUser
import com.example.datastore.ext.toModelUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.model.User as ModelUser

class UserDataStoreImpl@Inject constructor(
    private val dataStore: DataStore<SavedUsers>
): UserDataStore {
    override val savedUsers: Flow<SavedUsers>
        get() = dataStore.data

    override suspend fun saveUser(user: ModelUser) {
        dataStore.updateData {
            it.toBuilder()
                .addUser(
                    user.toDataStoreUser()
                ).build()
        }
    }

    override suspend fun saveUserList(users: List<ModelUser>) {
        dataStore.updateData {
            it.toBuilder()
                .addAllUser(users.map { user -> user.toDataStoreUser() })
                .build()
        }
    }

    override suspend fun getUserById(id: Int): ModelUser? {
        return dataStore.data
            .catch { exception ->
                throw exception
            }
            .map { savedUsers ->
                savedUsers.userList.find { it.id == id }?.toModelUser()
            }
            .firstOrNull()
    }
}