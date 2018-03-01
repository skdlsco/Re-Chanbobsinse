package com.edcan.chanbobsinse.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by eka on 2018. 2. 24..
 */
class Menu() : Parcelable {
    var title = ""
    var price = 0
    var isSelected = false

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        price = parcel.readInt()
        isSelected = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Menu> {
        override fun createFromParcel(parcel: Parcel): Menu {
            return Menu(parcel)
        }

        override fun newArray(size: Int): Array<Menu?> {
            return arrayOfNulls(size)
        }
    }
}