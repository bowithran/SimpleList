package com.example.model


import com.google.gson.annotations.SerializedName

data class UserEvent(
    @SerializedName("actor")
    val actor: Actor? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("payload")
    val payload: Payload? = null,
    @SerializedName("public")
    val isPublic: Boolean? = null,
    @SerializedName("repo")
    val repo: Repo? = null,
    @SerializedName("type")
    val type: String? = null
)