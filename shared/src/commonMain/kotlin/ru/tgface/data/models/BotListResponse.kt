package ru.tgface.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BotListItemResponse(
    @SerialName("id")
    val id: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("botName")
    val botName: String,
    @SerialName("createdAt")
    val createdAt: String,
)
