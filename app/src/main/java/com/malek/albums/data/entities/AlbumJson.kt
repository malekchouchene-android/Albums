package com.malek.albums.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.malek.albums.domain.Album

@Entity
data class AlbumJson(
    @SerializedName("albumId") val albumGroup: Int?,
    @SerializedName("id") @PrimaryKey val id: Long,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val imageUrl: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(albumGroup)
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AlbumJson> {
        override fun createFromParcel(parcel: Parcel): AlbumJson {
            return AlbumJson(parcel)
        }

        override fun newArray(size: Int): Array<AlbumJson?> {
            return arrayOfNulls(size)
        }
    }


}

