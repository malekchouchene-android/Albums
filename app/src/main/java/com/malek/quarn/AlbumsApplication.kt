package com.malek.quarn

import android.app.Application
import com.malek.quarn.di.*
import com.malek.quarn.utils.SchedulerProvider
import timber.log.Timber

class AlbumsApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        SchedulerProvider.init()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .appModule(AppModule())
            .dataModule(DataModule(this))
            .build()
    }
}