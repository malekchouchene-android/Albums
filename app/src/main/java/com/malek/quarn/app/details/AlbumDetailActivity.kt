package com.malek.quarn.app.details

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.malek.quarn.R
import com.malek.quarn.app.BaseActivity
import com.malek.quarn.data.entities.Album
import com.malek.quarn.databinding.ActivityAlbumDetailBinding

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
