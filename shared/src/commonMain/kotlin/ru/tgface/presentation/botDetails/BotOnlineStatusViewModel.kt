package ru.tgface.presentation.botDetails

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.ViewModel
import ru.tgface.presentation.emitState
import ru.tgface.presentation.models.BotStatus

class BotOnlineStatusViewModel(
    private val httpClient: TgFaceWebClient,
) : ViewModel {

    private val _state = MutableStateFlow<BotOnlineStatusState>(
        BotOnlineStatusState(
            isLoading = true,
            isError = false,
            isOnline = false,
            status = BotStatus.Suspend,
            pendingUpdateCount = 0,
            lastErrorMessage = null,
            lastErrorDate = null
        )
    )
    val state = _state.asStateFlow()

    suspend fun loadStatus(botId: Int) {
        _state.emitState {
            copy(
                isLoading = true,
                isError = false
            )
        }
        val res = httpClient.getBotOnlineStatus(botId).getOrNull()

        if (res != null) {
            _state.emitState {
                copy(
                    isLoading = false,
                    status = BotStatus.from(res.status) ?: BotStatus.Suspend,
                    isOnline = res.isOnline && res.isWebhookUrlValid,
                    pendingUpdateCount = res.pendingUpdateCount,
                    lastErrorMessage = res.lastErrorMessage,
                    lastErrorDate = res.lastErrorDate,
                )
            }
        } else {
            _state.emitState {
                copy(
                    isLoading = false,
                    isError = true,
                )
            }
        }
    }
}
