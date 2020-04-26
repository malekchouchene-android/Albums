package com.malek.quarn.data

import com.malek.quarn.data.entities.Album
import io.reactivex.Single
import retrofit2.http.GET

interface AlbumsApi {
    @GET("technical-test.json")
    fun getAlbumsList(): Single<List<Album>>
}