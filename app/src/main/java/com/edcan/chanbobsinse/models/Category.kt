package com.edcan.chanbobsinse.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by eka on 2018. 2. 22..
 */
class Category() : Parcelable {
    constructor(title: String, discription: String) : this() {
        this.title = title
        this.discription = discription
    }

    var title = ""
    var discription = ""
    var isSelected = false

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        discription = parcel.readString()
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(discription)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Category> {
        override fun createFromParcel(parcel: Parcel): Category {
            return Category(parcel)
        }

        override fun newArray(size: Int): Array<Category?> {
            return arrayOfNulls(size)
        }
    }
}