import androidx.compose.ui.window.ComposeUIViewController
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

@Suppress("unused")
fun MainViewController() = ComposeUIViewController { App() }

actual fun initLogger() {
    Napier.base(DebugAntilog())
}
