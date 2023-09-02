package ru.tgface.presentation.models

import androidx.compose.runtime.Stable

@Stable
data class BotRow(
    val id: Int,
    val name: String,
    val status: BotStatus,
)

enum class BotStatus(val value: Int) {
    Active(0),
    Deleted(1),
    Blocked(2),
    Suspend(3)
    ;

    companion object {

        fun from(value: Int) = BotStatus.values().firstOrNull { it.value == value }
    }
}
