package ru.tgface.presentation.botDetails

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.ViewModel
import ru.tgface.presentation.emitState
import ru.tgface.presentation.models.BotStatus

class BotContentListViewModel(
    private val httpClient: TgFaceWebClient,
) : ViewModel {

    private val _state = MutableStateFlow(
        BotContentListViewState(
            isLoading = true,
            isError = false,
            status = BotStatus.Suspend,
            numberOfSubscribers = 0,
            templates = listOf(),
        )
    )
    val state = _state.asStateFlow()

    suspend fun loadContent(botId: Int) {
        _state.emitState {
            copy(
                isLoading = true,
                isError = false
            )
        }
        val res = httpClient.getBotDetails(botId).getOrNull()

        if (res != null) {
            _state.emitState {
                copy(
                    isLoading = false,
                    status = BotStatus.from(res.status) ?: BotStatus.Suspend,
                    numberOfSubscribers = res.numberOfSubscribers,
                    templates = res.templates.map {
                        BotContentListTemplateViewState(
                            id = it.id,
                            name = it.name,
                            isStart = it.type == StartTemplateType,
                        )
                    }
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

    fun openTemplate(templateId: Int) = Unit
}

private const val StartTemplateType = 0

data class BotContentListViewState(
    val isLoading: Boolean,
    val isError: Boolean,
    val status: BotStatus,
    val numberOfSubscribers: Int,
    val templates: List<BotContentListTemplateViewState>,
)

data class BotContentListTemplateViewState(
    val id: Int,
    val name: String,
    val isStart: Boolean,
)
