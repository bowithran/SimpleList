package com.example.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.example.datastore.SavedUsers
import com.example.datastore.UserDataStore
import com.example.datastore.UserSerializer
import com.example.datastore.impl.UserDataStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun providesSavedUsersDataStore(
        @ApplicationContext context: Context,
    ): DataStore<SavedUsers> =
        DataStoreFactory.create(
            serializer = UserSerializer,
        ) {
            context.dataStoreFile(fileName = "saved_users.pb")
        }

    @Provides
    @Singleton
    internal fun providesUserDataStore(
        dataStore: DataStore<SavedUsers>
    ): UserDataStore = UserDataStoreImpl(dataStore)
}