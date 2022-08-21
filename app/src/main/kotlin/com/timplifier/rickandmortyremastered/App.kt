package com.timplifier.rickandmortyremastered

import android.app.Application
import com.timplifier.rickandmortyremastered.di.DaggerAppComponent


class App : Application() {
    val appComponent = DaggerAppComponent.create()
    override fun onCreate() {
        super.onCreate()

    }
}