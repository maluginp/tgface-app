import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.AppViewModel
import ru.tgface.presentation.NavigationRoute
import ru.tgface.presentation.SignInScreen

@Composable
fun App() {
    val appViewModel = remember { AppViewModel() }
    val routeNavigationKey = appViewModel.navigationKey.collectAsState()

    MaterialTheme {

        AnimatedContent(routeNavigationKey.value) { targetState ->
            when (targetState) {
                NavigationRoute.SignIn -> {
                    SignInScreen(appViewModel)
                }
                NavigationRoute.Dashboard -> {
                    Text("Dashboard")
                }
            }
        }
    }
}

expect fun getPlatformName(): String
