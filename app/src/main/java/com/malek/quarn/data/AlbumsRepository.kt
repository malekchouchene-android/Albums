package com.malek.quarn.data

import android.annotation.SuppressLint
import com.malek.quarn.data.entities.Album
import com.malek.quarn.utils.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

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
                Timber.e("api  error $t")
                getAlbumsFromDataBase()
            }

    }

    @SuppressLint("CheckResult")
    private fun saveAlbumsToDataBase(albums: List<Album>) {
        Completable.fromCallable {
            albumDao.insertAlbums(albums)
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

    private fun getAlbumsFromDataBase(): Single<List<Album>> {
        return Single.fromCallable {
            albumDao.getAlbums()
        }
    }

}