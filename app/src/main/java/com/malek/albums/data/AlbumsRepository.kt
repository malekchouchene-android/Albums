package com.malek.albums.data

import android.annotation.SuppressLint
import android.util.Log
import com.malek.albums.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface AlbumsRepository {

    fun getAlbumsList(): Single<List<Album>>
}


class AlbumsRepositoryImp(private val api: AlbumsApi, private val albumDao: AlbumDao) :
    AlbumsRepository {
    override fun getAlbumsList(): Single<List<Album>> {
        return api.getAlbumsList()
            .flatMap {
                saveAlbumsToDataBase(it)
                return@flatMap Single.just(it)
            }
            .onErrorResumeNext { t: Throwable ->
                Log.e("error loading from api", t.toString())
                getAlbumsFromDataBase()
            }

    }

    @SuppressLint("CheckResult")
    private fun saveAlbumsToDataBase(albums: List<Album>) {
        Completable.fromCallable {
            albumDao.insertAlbums(albums)
        }
            .subscribeOn(SchedulerProvider.getIo())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("new Row", "ok")
            }, {
                Log.e("new Row", it.toString())

            })
    }

    private fun getAlbumsFromDataBase(): Single<List<Album>> {
        return Single.fromCallable {
            albumDao.getAlbums()
        }
    }

}