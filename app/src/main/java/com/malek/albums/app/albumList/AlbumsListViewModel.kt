package com.malek.albums.app.albumList

import android.util.Log
import androidx.databinding.*
import androidx.lifecycle.*
import com.malek.albums.R
import com.malek.albums.app.utils.AutoBindViewModel
import com.malek.albums.data.entities.Album
import com.malek.albums.data.AlbumsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AlbumsListViewModelFactory(private val albumsRepository: AlbumsRepository) {
    fun supply(): AlbumsListViewModel {
        return AlbumsListViewModel(albumsRepository)
    }
}

class AlbumsListViewModel(private val albumsRepository: AlbumsRepository) : ViewModel(),
    LifecycleObserver {

    val albumsList: ObservableArrayList<AutoBindViewModel> = ObservableArrayList()
    val isLoading = ObservableBoolean(false)
    val lastScrollPosition = ObservableInt(0)
    private val compositeDisposable = CompositeDisposable()
    val albumClicked = MutableLiveData<Album?>()

    init {
        loadAlbumsList()
    }


    private fun loadAlbumsList(isRefresh: Boolean = false) {
        compositeDisposable.addAll(albumsRepository.getAlbumsList()
            .map { list ->
                list.map {
                    AlbumItemViewModel(it) { album ->
                        albumClicked.value = album
                    }
                }

            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading.set(true)
            }
            .doFinally {
                isLoading.set(false)
            }
            .subscribe({ list ->
                list?.let {
                    albumsList.clear()
                    albumsList.addAll(it)
                    if (isRefresh) lastScrollPosition.set(0)
                }


            }, {
                Timber.e("get albums error : $it")
            })
        )

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun resetLiveData() {
        albumClicked.value = null
    }

    fun onSwipeToRefresh() {
        loadAlbumsList(isRefresh = true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

data class AlbumItemViewModel(val album: Album, val onItemClickListener: ((Album) -> Unit)?) :
    AutoBindViewModel() {
    override fun areItemsTheSame(other: AutoBindViewModel): Boolean {
        return if (other is AlbumItemViewModel) {
            other.album.id == album.id
        } else {
            false
        }
    }


    override fun areContentsTheSame(other: AutoBindViewModel): Boolean {
        return if (other is AlbumItemViewModel) {
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

