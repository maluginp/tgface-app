package ru.tgface.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.tgface.presentation.models.BotStatus

@Composable
fun BotStatusView(
    status: BotStatus
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(when (status){
                BotStatus.Active -> Color.Green
                BotStatus.Deleted -> Color.Red
                BotStatus.Blocked -> Color.Red
                BotStatus.Suspend -> Color.Yellow
            })
            .padding(4.dp),
    ) {
        Text(
            text = when (status) {
                BotStatus.Active -> "Active"
                BotStatus.Deleted -> "Deleted"
                BotStatus.Blocked -> "Blocked"
                BotStatus.Suspend -> "Suspend"
            },
            color = Color.White,
        )
    }
}
