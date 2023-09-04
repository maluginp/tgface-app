package ru.tgface.presentation.botDetails

import androidx.compose.runtime.Stable
import ru.tgface.presentation.models.BotStatus

@Stable
data class BotOnlineStatusState(
    val isLoading: Boolean,
    val isError: Boolean,
    val status: BotStatus,
    val isOnline: Boolean,
    val isWebhookUrlValid: Boolean,
    val pendingUpdateCount: Int,
    val lastErrorMessage: String?,
    val lastErrorDate: Long?
)
