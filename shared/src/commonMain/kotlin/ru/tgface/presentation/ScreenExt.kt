package ru.tgface.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.compose.getKoin
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
inline fun <reified T : ViewModel> getViewModel(
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T {
    val koin = getKoin()
    return remember(key1 = qualifier?.value) { koin.get(qualifier, parameters) }
}


suspend inline fun <T> MutableStateFlow<T>.emitState(block: T.() -> T) {
    emit(this.value.block())
}
