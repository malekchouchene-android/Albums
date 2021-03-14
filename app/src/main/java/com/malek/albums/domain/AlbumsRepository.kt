package com.malek.albums.domain

import io.reactivex.Single

interface AlbumsRepository {

    fun getAlbumsList(): Single<List<Album>>
}