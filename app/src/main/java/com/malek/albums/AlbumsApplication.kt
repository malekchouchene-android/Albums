package com.malek.albums

import android.app.Application
import com.malek.albums.di.*
import com.malek.albums.utils.SchedulerProvider

class AlbumsApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        SchedulerProvider.init()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .appModule(AppModule())
            .dataModule(DataModule(this))
            .build()
    }
}