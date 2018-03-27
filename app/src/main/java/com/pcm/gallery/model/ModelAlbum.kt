package com.pcm.gallery.model

import android.os.Parcel
import android.os.Parcelable

data class ModelAlbum(var id: String,
                      var name: String,
                      var count: Int,
                      var type: Int,
                      var path: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeInt(count)
        parcel.writeInt(type)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelAlbum> {
        override fun createFromParcel(parcel: Parcel): ModelAlbum {
            return ModelAlbum(parcel)
        }

        override fun newArray(size: Int): Array<ModelAlbum?> {
            return arrayOfNulls(size)
        }
    }
}