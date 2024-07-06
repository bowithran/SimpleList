package com.example.domain.di

import com.example.data.repository.UserRepository
import com.example.domain.usecase.UserUseCase
import com.example.domain.usecase.impl.UserUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DomainModule {

    @Singleton
    @Provides
    fun provideUserUseCase(
        userRepository: UserRepository
    ): UserUseCase {
        return UserUseCaseImpl(
            userRepository
        )
    }
}