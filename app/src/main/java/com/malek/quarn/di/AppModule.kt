package com.malek.quarn.di

import com.malek.quarn.app.albumList.AlbumsListViewModelFactory
import com.malek.quarn.data.AlbumsRepository
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideAlbumsListViewModelFactory(albumsRepository: AlbumsRepository): AlbumsListViewModelFactory {
        return AlbumsListViewModelFactory(albumsRepository)
    }
}