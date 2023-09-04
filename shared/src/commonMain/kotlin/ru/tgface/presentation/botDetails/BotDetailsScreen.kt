package ru.tgface.presentation.botDetails

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun BotDetailsScreen(botId: Int) {

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("Details")
                },
                backgroundColor =  MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = {/* Do Something*/ }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        Text("BotDetails = $botId")

        BotOnlineStatusView(botId)
    }
}
