package ru.tgface.presentation

import androidx.compose.runtime.Stable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import ru.tgface.data.TgFaceWebClient

class AppViewModel {

    private val httpClient = TgFaceWebClient()

    private val _navigationKey = MutableStateFlow<NavigationRoute>(NavigationRoute.SignIn)
    val navigationKey = _navigationKey.asStateFlow()

    suspend fun navigate(key: NavigationRoute) {
        _navigationKey.emit(key)
    }

    suspend fun signIn(email: String, password: String) = withContext(Dispatchers.IO) {
        if (httpClient.signIn(email, password)) {
            withContext(Dispatchers.Main) {
                navigate(NavigationRoute.Dashboard)
            }
        }
    }
}

@Stable
sealed class NavigationRoute(val name: String) {

    data object SignIn : NavigationRoute("signin")
    data object Dashboard : NavigationRoute("dashboard")
}
