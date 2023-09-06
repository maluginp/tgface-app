package ru.tgface.presentation.botDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tgface.presentation.getViewModel

@Composable
fun BotDetailsScreen(botId: Int) {

    val viewModel = getViewModel<BotDetailsViewModel>()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("Details")
                },
                backgroundColor = MaterialTheme.colors.primarySurface,
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch {
                            viewModel.navigateToBack()
                        }
                    }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }
    ) {
        Column {
            BotContentListView(
                botId = botId,
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotOnlineStatusView(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                botId = botId,
            )
        }

    }
}
