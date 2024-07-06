package com.example.model


import com.google.gson.annotations.SerializedName

data class Payload(
    @SerializedName("forkee")
    val forkee: Forkee? = null
)