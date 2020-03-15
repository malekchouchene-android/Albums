package com.malek.albums.app.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.malek.albums.AlbumsApplication
import com.malek.albums.BR
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.malek.albums.app.albumList.AlbumItemViewModel
import com.squareup.picasso.Picasso


val Activity.injector
    get() = (application as AlbumsApplication).appComponent


val Context.isPortrait
    get() = this.resources.configuration.orientation==Configuration.ORIENTATION_PORTRAIT




