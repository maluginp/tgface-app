import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.koin.compose.getKoin
import ru.tgface.presentation.Navigation
import ru.tgface.presentation.NavigationRoute
import ru.tgface.presentation.botDetails.BotDetailsScreen
import ru.tgface.presentation.dashboard.DashboardScreen
import ru.tgface.presentation.signin.SignInScreen

@Composable
fun App() {
    val routeNavigationKey = getKoin().get<Navigation>().navigationKey.collectAsState()

    MaterialTheme {
        AnimatedContent(
            targetState = routeNavigationKey.value,
            transitionSpec = {
                (slideInHorizontally().togetherWith(slideOutHorizontally())).using(SizeTransform(clip = false))
            }
        ) { targetState ->
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

expect fun initLogger()
