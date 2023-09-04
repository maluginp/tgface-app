package ru.tgface.presentation.botDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
            Text("Status", style = MaterialTheme.typography.h6)
            Row {
                OnlineChipView(state.isOnline)
                Spacer(modifier = Modifier.width(4.dp))
                QueueChipView(state.pendingUpdateCount)
            }

            if (!state.isOnline) {
                Text(
                    text = "[${state.lastErrorDate?.formatDateTime()}] ${state.lastErrorMessage}",
                    color = Color.Red
                )
            }
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
private fun QueueChipView(
    count: Int
) {
    Row(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.primary)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.MailOutline,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$count in queue",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body1
        )
    }

}

@Composable
private fun OnlineChipView(isOnline: Boolean) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.small)
            .background(
                color = if (isOnline) {
                    Color.Green
                } else {
                    Color.Red
                }
            )
            .padding(vertical = 4.dp, horizontal = 8.dp),
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
