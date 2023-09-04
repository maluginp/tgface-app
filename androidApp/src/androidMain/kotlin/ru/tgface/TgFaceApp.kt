package ru.tgface

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import initLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import ru.tgface.di.initKoin

class TgFaceApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@TgFaceApp)
        }
        initLogger()
    }
}

fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE
