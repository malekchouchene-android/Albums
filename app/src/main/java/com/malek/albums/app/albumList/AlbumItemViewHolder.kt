package com.malek.albums.app.albumList

import com.malek.albums.R
import com.malek.albums.app.utils.AutoBindViewModel
import com.malek.albums.domain.Album

data class AlbumItemViewHolder(val album: Album, val onItemClickListener: ((Album) -> Unit)?) : AutoBindViewModel() {
    override fun areItemsTheSame(other: AutoBindViewModel): Boolean {
        return if (other is AlbumItemViewHolder) {
            other.album.id == album.id
        } else {
            false
        }
    }


    override fun areContentsTheSame(other: AutoBindViewModel): Boolean {
        return if (other is AlbumItemViewHolder) {
            other.album.imageUrl == album.imageUrl && other.album.title == album.title && other.album.thumbnailUrl == album.thumbnailUrl
        } else {
            false
        }
    }

    fun onItemClicked() {
        onItemClickListener?.invoke(album)
    }


    override val layout: Int
        get() = R.layout.album_item_layout
}