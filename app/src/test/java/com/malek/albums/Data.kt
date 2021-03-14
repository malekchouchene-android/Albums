package com.malek.albums

import com.malek.albums.data.entities.AlbumJson
import com.malek.albums.domain.Album

object Data {
    val album1 = Album(
        id = 1,
        thumbnailUrl = "url",
        title = "title",
        imageUrl = "url"
    )
    val album2 = Album(
        id = 2,
        thumbnailUrl = "url2",
        title = "title2",
        imageUrl = "url2"
    )
    val album3 = Album(
        id = 1,
        thumbnailUrl = "url",
        title = "title",
        imageUrl = "url2"
    )

    val albumJson1 = AlbumJson(
        id = 1,
        thumbnailUrl = "url",
        title = "title",
        albumGroup = 1,
        imageUrl = "url"
    )
    val albumJson2 = AlbumJson(
        id = 2,
        thumbnailUrl = "url2",
        title = "title",
        imageUrl = "url2",
        albumGroup = 2
    )
    val albumJson3 = AlbumJson(
        id = 3,
        thumbnailUrl = "url",
        title = "title",
        imageUrl = "url2",
        albumGroup = 3
    )
}
