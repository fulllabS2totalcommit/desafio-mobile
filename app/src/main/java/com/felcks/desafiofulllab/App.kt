package com.felcks.desafiofulllab

import android.app.Application
import com.felcks.desafiofulllab.di.DependencyModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@App)
            modules(listOf(DependencyModules.appModule))
        }
    }

    companion object {
        lateinit var instance: App
    }

    init {
        instance = this
    }
}