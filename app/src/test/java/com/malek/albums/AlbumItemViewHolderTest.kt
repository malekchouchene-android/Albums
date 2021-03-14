package com.malek.albums

import com.malek.albums.app.albumList.AlbumItemViewHolder
import com.malek.albums.app.utils.AutoBindViewHolderDiffCallBack
import org.junit.Test

class AlbumItemViewHolderTest {

    private val albumItemViewModel =
        AlbumItemViewHolder(album = Data.album1, onItemClickListener = {})
    private val albumItemViewModel2 =
        AlbumItemViewHolder(album = Data.album2, onItemClickListener = {})
    private val albumItemViewModel3 =
        AlbumItemViewHolder(album = Data.album3, onItemClickListener = {})

    @Test
    fun should_get_false_when_contents_album_not_same() {


        assert(!albumItemViewModel.areContentsTheSame(albumItemViewModel2))
        assert(!albumItemViewModel.areContentsTheSame(albumItemViewModel3))

    }

    @Test
    fun test_diff_utils() {

        val callBack = AutoBindViewHolderDiffCallBack(
            oldList = listOf(albumItemViewModel, albumItemViewModel2),
            newList = listOf(albumItemViewModel3)
        )
        assert(callBack.areItemsTheSame(0, 0))
        assert(!callBack.areContentsTheSame(0, 0))
        assert(!callBack.areItemsTheSame(1, 0))
        assert(!callBack.areContentsTheSame(1, 0))
        assert(callBack.newListSize == 1)
        assert(callBack.oldListSize == 2)
    }

}