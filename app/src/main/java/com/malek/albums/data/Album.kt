package com.malek.albums.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Entity
@Parcelize
data class Album(
    @SerializedName("albumId") val albumGroup: Int?,
    @SerializedName("id") @PrimaryKey val id: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val imageUrl: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?
) : Parcelable