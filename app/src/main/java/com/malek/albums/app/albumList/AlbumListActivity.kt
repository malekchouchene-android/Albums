package com.malek.albums.app.albumList

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.malek.albums.R
import com.malek.albums.app.BaseActivity
import com.malek.albums.app.injector
import com.malek.albums.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class AlbumListActivity : BaseActivity() {
    override fun inject() {
        injector.inject(this)
    }

    companion object {
        const val LAST_SCROLL_POSITION = "last_scroll_position"
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

        binding.model = viewModel
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val layoutManager = list_albums?.layoutManager
        val lastPosition = if (layoutManager is LinearLayoutManager) {
            layoutManager.findFirstVisibleItemPosition()
        } else {
            -1
        }
        outState.putInt(LAST_SCROLL_POSITION, lastPosition)
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val lastPosition = savedInstanceState.getInt(LAST_SCROLL_POSITION, -1)
        viewModel.lastScrollPosition.set(lastPosition)

    }
}




