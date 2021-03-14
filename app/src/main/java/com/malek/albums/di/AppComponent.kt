package com.malek.albums.di

import com.malek.albums.app.albumList.AlbumListActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class,DataModule::class,DomainModule::class])
interface AppComponent {
    fun inject(activity: AlbumListActivity)
}