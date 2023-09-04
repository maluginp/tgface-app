import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

@Composable
fun MainView() {
    App()
}

@Suppress("unused")
@Preview
@Composable
fun AppPreview() {
    App()
}

actual fun initLogger() {}
