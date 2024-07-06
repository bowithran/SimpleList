package com.example.network.retrofit

import com.example.model.User
import com.example.model.UserEvent
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GithubApis {
    @GET("users")
    suspend fun getUsers(@Header("Authorization") token: String): ArrayList<User>

    @GET("users/{login}/received_events")
    suspend fun getUserEvents(@Path("login") login: String): ArrayList<UserEvent>
}