package com.malek.albums


import com.malek.albums.data.*
import com.malek.albums.data.Networking.AlbumsApi
import com.malek.albums.data.database.AlbumDao
import com.malek.albums.data.entities.AlbumJson
import com.malek.albums.utils.SchedulerProvider
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.net.ConnectException

@RunWith(MockitoJUnitRunner::class)
class AlbumRepositoryTest {
    @Mock
    private lateinit var albumsApi: AlbumsApi

    @Mock
    private lateinit var dao: AlbumDao


    @Before
    fun setUp() {
        SchedulerProvider.init(true)


    }

    @Test
    fun should_call_database_when_api_is_down() {
        //given
        val repository = AlbumsRepositoryImp(albumsApi, dao)

        BDDMockito.given(albumsApi.getAlbumsList()).willReturn(Single.error(Throwable()))
        //when
        repository.getAlbumsList().test()
        //then
        verify(dao, atLeastOnce()).getAlbums()

    }

    @Test
    fun should_insert_into_data_when_api_return_albums() {
        //given
        val repository = AlbumsRepositoryImp(albumsApi, dao)
        BDDMockito.given(albumsApi.getAlbumsList()).willReturn(Single.just(listOf(Data.albumJson1)))

        //when
        repository.getAlbumsList().test()

        //then
        verify(dao).insertAlbums(BDDMockito.anyList())


    }

    @Test
    fun should_drop_title_null_album_json() {
        val repository = AlbumsRepositoryImp(albumsApi, dao)
        BDDMockito.given(albumsApi.getAlbumsList())
            .willReturn(Single.just(listOf(Data.albumJson1.copy(title = null))))

        repository.getAlbumsList().test()
            .assertNoErrors()
            .assertValue {
                it.isEmpty()
            }


    }

    @Test
    fun should_get_error_when_api_down_and_db_empty() {
        val repository = AlbumsRepositoryImp(albumsApi, dao)

        BDDMockito.given(albumsApi.getAlbumsList()).willReturn(Single.error(ConnectException()))
        BDDMockito.given(dao.getAlbums()).willReturn(emptyList())
        repository.getAlbumsList().test()
            .assertError {
                it is ConnectException
            }
    }

    @Test
    fun should_not_call_insert_when_api_is_down() {
        val repository = AlbumsRepositoryImp(albumsApi, dao)

        BDDMockito.given(albumsApi.getAlbumsList()).willReturn(Single.error(Throwable()))
        //when
        repository.getAlbumsList().test()
        //then
        verify(dao, never()).insertAlbums(BDDMockito.anyList())
    }


}

