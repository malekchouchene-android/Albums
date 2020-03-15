package com.malek.albums.data

import com.malek.albums.data.models.Album
import io.reactivex.Single
import retrofit2.http.GET

interface AlbumsApi {
    @GET("technical-test.json")
    fun getAlbumsList(): Single<List<Album>>
}