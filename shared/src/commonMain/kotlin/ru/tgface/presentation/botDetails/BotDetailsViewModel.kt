package ru.tgface.presentation.botDetails

import ru.tgface.presentation.Navigation
import ru.tgface.presentation.ViewModel

class BotDetailsViewModel(
    private val navigation: Navigation,
) : ViewModel {

    suspend fun navigateToBack() {
        navigation.back()
    }
}
