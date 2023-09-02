import androidx.compose.animation.AnimatedContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import org.koin.compose.getKoin
import org.koin.core.context.startKoin
import ru.tgface.di.appModule
import ru.tgface.presentation.Navigation
import ru.tgface.presentation.NavigationRoute
import ru.tgface.presentation.botDetails.BotDetailsScreen
import ru.tgface.presentation.dashboard.DashboardScreen
import ru.tgface.presentation.signin.SignInScreen

@Composable
fun App() {
    val routeNavigationKey = getKoin().get<Navigation>().navigationKey.collectAsState()

    MaterialTheme {
        AnimatedContent(routeNavigationKey.value) { targetState ->
            when (targetState) {
                NavigationRoute.SignIn -> {
                    SignInScreen()
                }

                NavigationRoute.Dashboard -> {
                    DashboardScreen()
                }

                is NavigationRoute.BotDetails -> {
                    BotDetailsScreen(targetState.botId)
                }
            }
        }
    }
}

expect fun getPlatformName(): String
