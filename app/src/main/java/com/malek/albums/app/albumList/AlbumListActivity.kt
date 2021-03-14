package com.malek.albums.app.albumList

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.malek.albums.R
import com.malek.albums.app.BaseActivity
import com.malek.albums.app.details.AlbumDetailActivity
import com.malek.albums.app.utils.injector
import com.malek.albums.databinding.ActivityMainBinding
import javax.inject.Inject

class AlbumListActivity : BaseActivity() {
    override fun inject() {
        injector.inject(this)
    }

    companion object {
        const val LAST_SCROLL_POSITION = "last_scroll_position"
        const val DEFAULT_SCROLL_VALUE = -1
    }


    @Inject
    lateinit var albumsListViewModelFactory: AlbumsListViewModelFactory
    private val viewModel: AlbumsListViewModel by viewModels {
        object : ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return albumsListViewModelFactory.supply() as T
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(viewModel)
        binding.model = viewModel
        viewModel.albumClicked.observe(this, Observer { album ->
            album.let {
                val intent = Intent(this, AlbumDetailActivity::class.java)
                intent.putExtra(AlbumDetailActivity.ALBUM_EXTRA, it)
                startActivity(intent)
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = findViewById<RecyclerView>(R.id.list_albums)?.layoutManager
        val lastPosition = if (layoutManager is LinearLayoutManager) {
            layoutManager.findFirstVisibleItemPosition()
        } else {
            DEFAULT_SCROLL_VALUE
        }
        outState.putInt(LAST_SCROLL_POSITION, lastPosition)
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val lastPosition = savedInstanceState.getInt(LAST_SCROLL_POSITION, DEFAULT_SCROLL_VALUE)
        viewModel.lastScrollPosition.set(lastPosition)

    }
}





