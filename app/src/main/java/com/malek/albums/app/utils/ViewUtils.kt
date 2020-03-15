package com.malek.albums.app.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindUrlImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).into(imageView)
}