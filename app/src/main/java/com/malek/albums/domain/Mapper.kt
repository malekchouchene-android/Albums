package com.malek.albums.domain

import com.malek.albums.data.entities.AlbumJson


fun AlbumJson.toDomaine(): Album? {
    return title?.let {
        Album(id = id, title = title, imageUrl = imageUrl, thumbnailUrl = thumbnailUrl)

    }

}