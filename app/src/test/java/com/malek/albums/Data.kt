package com.malek.albums

import com.malek.albums.data.models.Album
object Data{
    val album1 = Album(
        albumGroup = 1,
        id = 1,
        thumbnailUrl = "url",
        title = "title",
        imageUrl = "url"
    )
    val album2 = Album(
        albumGroup = 2,
        id = 2,
        thumbnailUrl = "url2",
        title = "title2",
        imageUrl = "url2"
    )
    val album3 = Album(
        albumGroup = 1,
        id = 1,
        thumbnailUrl = "url",
        title = "title",
        imageUrl = "url2"
    )
}
