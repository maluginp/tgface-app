package ru.tgface.presentation

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

interface Navigation  {

    val navigationKey: StateFlow<NavigationRoute>
    suspend fun to(key: NavigationRoute)
    suspend fun replace(key: NavigationRoute)
    suspend fun back()
}

internal class NavigationImpl : Navigation {

    private val _navigationKey = MutableStateFlow<NavigationRoute>(NavigationRoute.SignIn)
    override val navigationKey = _navigationKey.asStateFlow()

    private var _stack = mutableListOf<NavigationRoute>(NavigationRoute.SignIn)

    override suspend fun to(key: NavigationRoute) {
        _stack.add(key)
        _navigationKey.emit(key)
    }

    override suspend fun replace(key: NavigationRoute) {
        _stack = mutableListOf(key)
        _navigationKey.emit(key)
    }

    override suspend fun back() {
        if (_stack.size > 1) {
            _stack.removeLast()
            val key =_stack[_stack.size - 1]
            _navigationKey.emit(key)
        }
    }
}

@Stable
sealed class NavigationRoute(val name: String) {

    data object SignIn : NavigationRoute("signin")
    data object Dashboard : NavigationRoute("dashboard")

    data class BotDetails(val botId: Int) : NavigationRoute("bot_details")
}
