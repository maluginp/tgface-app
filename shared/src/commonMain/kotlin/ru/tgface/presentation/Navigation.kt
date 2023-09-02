package ru.tgface.presentation

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface Navigation  {

    val navigationKey: StateFlow<NavigationRoute>
    suspend fun to(key: NavigationRoute)
}

internal class NavigationImpl : Navigation {

    private val _navigationKey = MutableStateFlow<NavigationRoute>(NavigationRoute.SignIn)
    override val navigationKey = _navigationKey.asStateFlow()

    override suspend fun to(key: NavigationRoute) {
        _navigationKey.emit(key)
    }
}

@Stable
sealed class NavigationRoute(val name: String) {

    data object SignIn : NavigationRoute("signin")
    data object Dashboard : NavigationRoute("dashboard")

    data class BotDetails(val botId: Int) : NavigationRoute("bot_details")
}
