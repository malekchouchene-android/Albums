package com.malek.albums.app.albumList

import android.widget.TextView
import androidx.annotation.StringRes
import androidx.databinding.*
import androidx.lifecycle.*
import com.malek.albums.R
import com.malek.albums.app.utils.AutoBindViewModel
import com.malek.albums.domain.Album
import com.malek.albums.domain.AlbumsRepository
import com.malek.albums.domain.GetAlbumsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException


class AlbumsListViewModelFactory(private val getAlbumsUseCase: GetAlbumsUseCase) {
    fun supply(): AlbumsListViewModel {
        return AlbumsListViewModel(getAlbumsUseCase)
    }
}

class AlbumsListViewModel(private val getAlbumsUseCase: GetAlbumsUseCase) : ViewModel(),
    LifecycleObserver {

    val albumsList: ObservableArrayList<AutoBindViewModel> = ObservableArrayList()
    val isLoading = ObservableBoolean(false)
    val lastScrollPosition = ObservableInt(0)
    private val compositeDisposable = CompositeDisposable()
    private val _albumClicked: MutableLiveData<Album> = MutableLiveData()
    val albumClicked: LiveData<Album> = _albumClicked
    val emptyStatVisibility = ObservableBoolean(false)
    val emptyStatText: ObservableField<Int?> = ObservableField()


    init {
        loadAlbumsList()
    }


    private fun loadAlbumsList(isRefresh: Boolean = false) {
        compositeDisposable.addAll(getAlbumsUseCase.execute()
            .map { list ->
                list.map {
                    AlbumItemViewHolder(it) { album ->
                        _albumClicked.value = album
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
                emptyStatVisibility.set(false)
                emptyStatText.set(null)
                albumsList.clear()
                albumsList.addAll(list)
                if (isRefresh) lastScrollPosition.set(0)


            }, {
                emptyStatVisibility.set(true)
                emptyStatText.set(formatErorr(it))
            })
        )

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun resetLiveData() {
        _albumClicked.value = null
    }

    fun onSwipeToRefresh() {
        loadAlbumsList(isRefresh = true)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun formatErorr(throwable: Throwable): Int {

        return when (throwable) {
            is ConnectException, is SocketTimeoutException -> R.string.no_network_error
            else -> R.string.unexpected_error
        }
    }
}



