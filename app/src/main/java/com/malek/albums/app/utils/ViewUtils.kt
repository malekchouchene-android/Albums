package com.malek.albums.app.utils

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.malek.albums.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindUrlImage(imageView: ImageView, url: String?) {
    Picasso.get().load(url).placeholder(ColorDrawable(ContextCompat.getColor(imageView.context,R.color.placeholder))).into(imageView)
}
@BindingAdapter("textRes")
fun setTextRes(textView: TextView, @StringRes textRes: Int?) {
    textRes?.let {
        textView.setText(textRes)
    }
}
