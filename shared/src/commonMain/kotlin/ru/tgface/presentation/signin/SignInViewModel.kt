package ru.tgface.presentation.signin

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.Navigation
import ru.tgface.presentation.NavigationRoute
import ru.tgface.presentation.ViewModel

class SignInViewModel (
    private val httpClient: TgFaceWebClient,
    private val navigation: Navigation,
) : ViewModel {


    suspend fun signIn(email: String, password: String) = withContext(Dispatchers.IO) {
        if (httpClient.signIn(email, password)) {
            withContext(Dispatchers.Main) {
                navigation.replace(NavigationRoute.Dashboard)
            }
        }
    }

    suspend fun checkAuth() {
        if (httpClient.hasToken()) {
            navigation.replace(NavigationRoute.Dashboard)
        }
    }
}

