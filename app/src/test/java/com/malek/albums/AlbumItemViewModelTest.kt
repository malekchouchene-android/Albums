package com.malek.albums

import com.malek.albums.app.AutoBindViewModelDiffCallBack
import com.malek.albums.app.albumList.AlbumItemViewModel
import org.junit.Test

class AlbumItemViewModelTest {

    private val albumItemViewModel = AlbumItemViewModel(album = Data.album1,onItemClickListener = {})
    private val albumItemViewModel2 = AlbumItemViewModel(album = Data.album2,onItemClickListener = {})
    private val albumItemViewModel3 = AlbumItemViewModel(album = Data.album3,onItemClickListener = {})
    @Test
    fun should_get_false_when_contents_album_not_same() {


        assert(!albumItemViewModel.areContentsTheSame(albumItemViewModel2))
        assert(!albumItemViewModel.areContentsTheSame(albumItemViewModel3))

    }

    @Test
    fun test_diff_utils() {

        val callBack = AutoBindViewModelDiffCallBack(
            oldList = listOf(albumItemViewModel, albumItemViewModel2),
            newList = listOf(albumItemViewModel3)
        )
        assert(callBack.areItemsTheSame(0, 0))
        assert(!callBack.areContentsTheSame(0, 0))
        assert(!callBack.areItemsTheSame(1, 0))
        assert(!callBack.areContentsTheSame(1, 0))
        assert(callBack.newListSize==1)
        assert(callBack.oldListSize==2)
    }

}