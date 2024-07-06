package com.example.data.di

import com.example.data.repository.UserRepository
import com.example.data.repository.impl.UserRepositoryImpl
import com.example.datastore.UserDataStore
import com.example.network.retrofit.GithubApis
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataModule {

    @Singleton
    @Provides
    fun provideGithubRepository(
        githubApis: GithubApis,
        dataStore: UserDataStore,
    ): UserRepository {
        return UserRepositoryImpl(
            githubApis,
            dataStore,
        )
    }
}