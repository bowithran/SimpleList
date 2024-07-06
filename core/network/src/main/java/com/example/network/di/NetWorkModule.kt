package com.example.network.di

import com.example.network.retrofit.GithubApis
import com.example.network.retrofit.URLs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetWorkModule {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl(URLs.GITHUB_BASE_URL)
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    @Singleton
    @Provides
    fun provideGithubApi(
        retrofit: Retrofit
    ): GithubApis = retrofit.create(GithubApis::class.java)
}