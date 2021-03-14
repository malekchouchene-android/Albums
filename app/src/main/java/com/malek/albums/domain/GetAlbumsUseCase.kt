package com.malek.albums.domain

import io.reactivex.Observable
import io.reactivex.Single

class GetAlbumsUseCase(private val albumsRepository: AlbumsRepository){
     fun execute(): Single<List<Album>> {
        return albumsRepository.getAlbumsList()

    }
}