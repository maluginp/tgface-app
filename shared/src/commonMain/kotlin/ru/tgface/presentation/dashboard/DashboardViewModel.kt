package ru.tgface.presentation.dashboard

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.Navigation
import ru.tgface.presentation.NavigationRoute
import ru.tgface.presentation.ViewModel
import ru.tgface.presentation.models.BotRow
import ru.tgface.presentation.models.BotStatus

class DashboardViewModel(
    private val httpClient: TgFaceWebClient,
    private val navigation: Navigation,
) : ViewModel {

    private val _bots = MutableStateFlow<List<BotRow>>(listOf())
    val bots = _bots.asStateFlow()
    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    suspend fun loadBots() {
        _isLoading.emit(true)
        _bots.emit(httpClient.getBotList().map {
            BotRow(
                id = it.id,
                name = it.botName,
                status = BotStatus.from(it.status) ?: BotStatus.Suspend,
            )
        })
        _isLoading.emit(false)
    }

    suspend fun openBot(id: Int) {
        navigation.to(NavigationRoute.BotDetails(id))
    }
}
