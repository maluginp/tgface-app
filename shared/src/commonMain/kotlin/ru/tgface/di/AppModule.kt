package ru.tgface.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.tgface.data.TgFaceWebClient
import ru.tgface.presentation.signin.SignInViewModel
import ru.tgface.presentation.dashboard.DashboardViewModel
import ru.tgface.presentation.Navigation
import ru.tgface.presentation.NavigationImpl


fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            appModule,
        )
    }


val appModule = module {
    factory {
        SignInViewModel(get(), get())
    }

    factory {
        DashboardViewModel(get(), get())
    }

    single {
        TgFaceWebClient()
    }

    single {
        NavigationImpl()
    } bind Navigation::class
}

fun initKoin() = initKoin {}
