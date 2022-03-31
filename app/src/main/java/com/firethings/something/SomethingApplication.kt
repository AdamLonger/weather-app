package com.firethings.something

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class SomethingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SomethingApplication)
            modules(myModule)
        }

        Timber.plant(Timber.DebugTree())
    }
}
