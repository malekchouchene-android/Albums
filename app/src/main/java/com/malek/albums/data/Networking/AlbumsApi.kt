package com.malek.albums.data.Networking

import com.malek.albums.data.entities.AlbumJson
import io.reactivex.Single
import retrofit2.http.GET

interface AlbumsApi {
    @GET("technical-test.json")
    fun getAlbumsList(): Single<List<AlbumJson>>
}