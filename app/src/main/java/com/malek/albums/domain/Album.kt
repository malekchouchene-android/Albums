package com.malek.albums.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Album(
    val id: Long,
    val title: String,
    val imageUrl: String?,
    val thumbnailUrl: String?
) : Parcelable
