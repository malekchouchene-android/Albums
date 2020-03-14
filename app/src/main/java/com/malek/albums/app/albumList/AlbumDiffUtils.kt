package com.malek.albums.app.albumList

import androidx.recyclerview.widget.DiffUtil

class AlbumDiffUtilsCallback(
    private val oldList: List<AlbumItemViewModel>,
    private val newList: List<AlbumItemViewModel>
) :
    DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].album.id == newList[newItemPosition].album.id
    }

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldIAlbum = oldList[oldItemPosition].album
        val newAlbum = newList[newItemPosition].album
        return oldIAlbum.imageUrl == newAlbum.imageUrl && oldIAlbum.title == newAlbum.title && newAlbum.thumbnailUrl == oldIAlbum.thumbnailUrl
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return newList[newItemPosition]
    }
}