package com.malek.albums.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.malek.albums.data.entities.Album

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}