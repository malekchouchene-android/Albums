package com.malek.albums.app.albumList

import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import com.malek.albums.R
import com.malek.albums.app.AutoBindViewModel
import com.malek.albums.data.Album
import com.malek.albums.data.AlbumsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AlbumsListViewModelFactory(private val albumsRepository: AlbumsRepository) {
    fun supply(): AlbumsListViewModel {
        return AlbumsListViewModel(albumsRepository)
    }
}

class AlbumsListViewModel(private val albumsRepository: AlbumsRepository) : ViewModel() {
    val albumsList = ObservableArrayList<AlbumItemViewModel>()
    val isLoading = ObservableBoolean(false)
    val lastScrollPosition = ObservableInt(0)
    private val compositeDisposable = CompositeDisposable()

    init {
        loadAlbumsList()
    }


    private fun loadAlbumsList(isRefresh: Boolean = false) {
        compositeDisposable.addAll(albumsRepository.getAlbumsList()
            .map { list ->
                list.map {
                    AlbumItemViewModel(it)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                albumsList.clear()
                isLoading.set(true)
            }
            .doFinally {
                isLoading.set(false)
            }
            .subscribe(
                {
                    albumsList.addAll(it)
                    if (isRefresh) lastScrollPosition.set(0)

                }, {
                    Log.e("error", it.toString())
                })
        )

    }

    fun onSwipeToRefresh() {
        loadAlbumsList(isRefresh = true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

data class AlbumItemViewModel(val album: Album) : AutoBindViewModel() {
    override val layout: Int
        get() = R.layout.album_item_layout

}
