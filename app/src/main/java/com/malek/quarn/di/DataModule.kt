package com.malek.quarn.di

import androidx.room.Room
import com.malek.quarn.AlbumsApplication
import com.malek.quarn.data.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule(private val application: AlbumsApplication) {
    @Provides
    fun provideAlbumsRepository(api: AlbumsApi, albumDao: AlbumDao): AlbumsRepository {
        return AlbumsRepositoryImp(api, albumDao)
    }

    @Provides
    @Singleton
    fun provideAlbumDatabase(): AlbumDatabase {
        return Room.databaseBuilder(
            application, AlbumDatabase::class.java, "album_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideAlbumDao(albumDatabase: AlbumDatabase): AlbumDao {
        return albumDatabase.albumDao()
    }
}