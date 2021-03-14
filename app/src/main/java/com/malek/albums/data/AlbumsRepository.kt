package com.malek.albums.data

import android.annotation.SuppressLint
import com.malek.albums.data.Networking.AlbumsApi
import com.malek.albums.data.database.AlbumDao
import com.malek.albums.data.entities.AlbumJson
import com.malek.albums.domain.Album
import com.malek.albums.domain.AlbumsRepository
import com.malek.albums.domain.toDomaine
import com.malek.albums.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber


class AlbumsRepositoryImp(private val api: AlbumsApi, private val albumDao: AlbumDao) :
    AlbumsRepository {
    override fun getAlbumsList(): Single<List<Album>> {
        return api.getAlbumsList()
            .flatMap {
                saveAlbumsToDataBase(it)
                return@flatMap Single.just(it)
            }
            .onErrorResumeNext { throwable ->
                getAlbumsFromDataBase()
                    .map {
                        if (it.isEmpty()) throw throwable else it
                    }
            }
            .map {
                it.mapNotNull { albumJson -> albumJson.toDomaine() }
            }

    }

    @SuppressLint("CheckResult")
    private fun saveAlbumsToDataBase(albumJsons: List<AlbumJson>) {
        Completable.fromCallable {
            albumDao.insertAlbums(albumJsons)
        }
            .toSingleDefault(true)
            .flatMap {
                Single.fromCallable {
                    albumDao.getAlbumsSize()
                }
            }
            .subscribeOn(SchedulerProvider.getIo())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.i("total number of Rows $it")
            }, {
                Timber.e("insert error $it")

            })
    }

    private fun getAlbumsFromDataBase(): Single<List<AlbumJson>> {
        return Single.fromCallable {
            albumDao.getAlbums()
        }
    }

}