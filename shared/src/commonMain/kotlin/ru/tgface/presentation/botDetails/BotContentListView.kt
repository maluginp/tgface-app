package ru.tgface.presentation.botDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tgface.presentation.getViewModel

@Composable
internal fun BotContentListView(
    modifier: Modifier = Modifier,
    botId: Int,
) {
    val viewModel = getViewModel<BotContentListViewModel>()
    val scope = rememberCoroutineScope()
    val state by viewModel.state.collectAsState()


    LaunchedEffect(scope) {
        viewModel.loadContent(botId)
    }

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.isError) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text("Error")
                Button(onClick = {
                    scope.launch {
                        viewModel.loadContent(botId)
                    }
                }) {
                    Text("Retry")
                }
            }
        }
    } else {
        Column(modifier = modifier) {

            SubscribersChipView(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                count = state.numberOfSubscribers,
            )

            Text(
                "Content",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )

            state.templates.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            viewModel.openTemplate(it.id)
                        }.padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = if (it.isStart) {
                            "Start"
                        } else {
                            it.name
                        }
                    )

                    Icon(
                        Icons.Filled.ArrowForward,
                        contentDescription = null,
                    )
                }
                Divider()
            }
        }
    }
}

@Composable
private fun SubscribersChipView(
    modifier: Modifier = Modifier,
    count: Int
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .background(MaterialTheme.colors.primary)
            .padding(vertical = 4.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.Face,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colors.onPrimary,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$count subscribers",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.body1
        )
    }

}

