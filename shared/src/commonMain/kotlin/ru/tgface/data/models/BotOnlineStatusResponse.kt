package ru.tgface.data.models

import kotlinx.serialization.Serializable

@Serializable
data class BotOnlineStatusResponse(
    val status: Int,
    val isOnline: Boolean,
    val isWebhookUrlValid: Boolean,
    val pendingUpdateCount: Int,
    val lastErrorMessage: String?,
    val lastErrorDate: Long?
)
