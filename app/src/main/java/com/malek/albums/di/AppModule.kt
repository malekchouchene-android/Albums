package com.malek.albums.di

import com.malek.albums.app.albumList.AlbumsListViewModelFactory
import com.malek.albums.data.AlbumsRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideAlbumsListViewModelFactory(albumsRepository: AlbumsRepository): AlbumsListViewModelFactory {
        return AlbumsListViewModelFactory(albumsRepository)
    }
}