package ru.tgface.presentation.botDetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.tgface.presentation.getViewModel

@Composable
internal fun BotOnlineStatusView(
    botId: Int
) {
    val viewModel = getViewModel<BotOnlineStatusViewModel>()
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(scope) {
        viewModel.loadStatus(botId)
    }



    if (state.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.isError) {
        //
    } else {
        Column {
            Text("Online ${state.isOnline}")
            Text("Webhook valid ${state.isWebhookUrlValid}")
            Text("Pending update count ${state.pendingUpdateCount}")
        }
    }
}
