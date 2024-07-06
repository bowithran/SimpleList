package com.example.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null
)