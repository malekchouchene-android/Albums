package com.malek.albums.data.database

import androidx.room.*
import com.malek.albums.data.entities.AlbumJson

@Dao
interface AlbumDao {
    @Query("SELECT * FROM AlbumJson")
    fun getAlbums(): List<AlbumJson>

    @Query("SELECT COUNT(*) FROM AlbumJson")
    fun getAlbumsSize(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albumJsons: List<AlbumJson>)
}

