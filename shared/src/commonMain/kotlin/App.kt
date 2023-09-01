import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun App() {
    MaterialTheme {
        SignInScreen()
    }
}

expect fun getPlatformName(): String
