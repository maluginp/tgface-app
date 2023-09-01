package ru.tgface.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("token")
    val token: String,
)
