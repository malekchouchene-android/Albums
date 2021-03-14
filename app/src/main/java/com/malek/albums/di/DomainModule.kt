package com.malek.albums.di

import com.malek.albums.domain.AlbumsRepository
import com.malek.albums.domain.GetAlbumsUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {
    @Provides
    fun provideGetalbumsUseCase(albumsRepository: AlbumsRepository): GetAlbumsUseCase {
        return GetAlbumsUseCase(albumsRepository)
    }
}