package ru.tgface.presentation.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.tgface.presentation.getViewModel

@Composable
fun DashboardScreen() {
    val viewModel = getViewModel<DashboardViewModel>()
    val scope = rememberCoroutineScope()
    val isLoading by viewModel.isLoading.collectAsState()
    val bots by viewModel.bots.collectAsState()

    LaunchedEffect(scope) {
        viewModel.loadBots()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                elevation = 4.dp,
                title = {
                    Text("Dashboard")
                },
                backgroundColor =  MaterialTheme.colors.primarySurface,
                actions = {
                    IconButton(
                        modifier = Modifier.size(32.dp),
                        onClick = {
                            scope.launch {
                                viewModel.logout()
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                        )
                    }
                }
            )
        },

    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(bots) { bot ->
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .clickable {
                                scope.launch {
                                    viewModel.openBot(bot.id)
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    ) {
                        Text("#${bot.id} ${bot.name}", style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.height(4.dp))
                        BotStatusView(bot.status)
                    }
                    Divider(modifier = Modifier.height(1.dp).fillMaxWidth())
                }
            }
        }
    }
}
