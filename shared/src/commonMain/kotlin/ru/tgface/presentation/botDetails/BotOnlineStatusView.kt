package ru.tgface.presentation.botDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.ktor.util.date.GMTDate
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import ru.tgface.presentation.getViewModel

@Composable
internal fun BotOnlineStatusView(
    modifier: Modifier = Modifier,
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
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.isError) {
        //
    } else {
        Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Online Status", style = MaterialTheme.typography.h6)
            OnlineStatus(state.isOnline)
            if (!state.isOnline) {
                Text(
                    text = "[${state.lastErrorDate?.formatDateTime()}] ${state.lastErrorMessage}",
                    color = Color.Red
                )
            }

            Text("Pending update count ${state.pendingUpdateCount}")
        }
    }
}

private fun Long?.formatDateTime(): String {
    if (this == null) return ""
    val instant = Instant.fromEpochSeconds(this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.toString()
}

@Composable
private fun OnlineStatus(isOnline: Boolean) {
    Box(
        modifier = Modifier.width(100.dp)
            .clip(MaterialTheme.shapes.small)
            .background(
                color = if (isOnline) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
            .padding(4.dp),
        contentAlignment = Alignment.Center,
    ) {

        Text(
            text = if (isOnline) {
                "Online"
            } else {
                "Offline"
            },
            color = if (isOnline) {
                Color.Black
            } else {
                Color.White
            },
            style = MaterialTheme.typography.body1
        )
    }
}
