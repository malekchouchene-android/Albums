package com.malek.albums.app.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.malek.albums.R
import com.malek.albums.app.BaseActivity
import com.malek.albums.data.models.Album
import com.malek.albums.databinding.ActivityAlbumDetailBinding

class AlbumDetailActivity : BaseActivity() {

    companion object {
        const val ALBUM_EXTRA = "album_extra"

    }

    override fun inject() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityAlbumDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_album_detail)
        intent?.getParcelableExtra<Album>(ALBUM_EXTRA)?.let {
            binding.album = it
        } ?: kotlin.run {
            finish()
        }
    }
}
