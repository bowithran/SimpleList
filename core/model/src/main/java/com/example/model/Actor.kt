package com.example.model

import com.google.gson.annotations.SerializedName

data class Actor(
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("display_login")
    val displayLogin: String? = null,
    @SerializedName("gravatar_id")
    val gravatarId: String? = null,
    val id: Int? = null,
    val login: String? = null,
    val url: String? = null
)