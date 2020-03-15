package com.malek.albums.data

import androidx.room.*
import com.malek.albums.data.models.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM Album")
    fun getAlbums(): List<Album>

    @Query("SELECT COUNT(*) FROM Album")
    fun getAlbumsSize(): Int

    @Insert
    fun insertAlbums(albums: List<Album>)
}

@Database(entities = arrayOf(Album::class), version = 1)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}