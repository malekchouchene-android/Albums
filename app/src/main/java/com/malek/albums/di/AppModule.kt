package com.malek.albums.di

import com.malek.albums.app.albumList.AlbumsListViewModelFactory
import com.malek.albums.domain.AlbumsRepository
import com.malek.albums.domain.GetAlbumsUseCase
import dagger.Module
import dagger.Provides

@Module
class AppModule {
    @Provides
    fun provideAlbumsListViewModelFactory(getAlbumsUseCase: GetAlbumsUseCase): AlbumsListViewModelFactory {
        return AlbumsListViewModelFactory(getAlbumsUseCase)
    }
}