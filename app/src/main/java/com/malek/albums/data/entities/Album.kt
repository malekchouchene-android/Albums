package com.malek.albums.data.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import com.google.gson.annotations.SerializedName

@Entity
data class Album(
    @SerializedName("albumId") val albumGroup: Int?,
    @SerializedName("id") @PrimaryKey val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val imageUrl: String?,
    @SerializedName("thumbnailUrl") val thumbnailUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(albumGroup)
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(imageUrl)
        parcel.writeString(thumbnailUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}

