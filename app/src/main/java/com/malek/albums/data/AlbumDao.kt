package com.malek.albums.data

import androidx.room.*
import com.malek.albums.data.entities.Album

@Dao
interface AlbumDao {
    @Query("SELECT * FROM Album")
    fun getAlbums(): List<Album>

    @Query("SELECT COUNT(*) FROM Album")
    fun getAlbumsSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albums: List<Album>)
}

@Database(entities = [Album::class], version = 1)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun albumDao(): AlbumDao
}