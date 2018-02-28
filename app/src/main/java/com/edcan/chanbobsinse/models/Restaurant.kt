package com.edcan.chanbobsinse.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by eka on 2018. 2. 24..
 */
class Restaurant() : Parcelable {
    var title = ""
    var phone = ""
    var address = ""
    var categories = ArrayList<Category>()
    var rating = 0f
    var menus = ArrayList<Menu>()

    constructor(parcel: Parcel) : this() {
        title = parcel.readString()
        phone = parcel.readString()
        address = parcel.readString()
        categories = ArrayList()
        parcel.readTypedList(categories, Category.CREATOR)
        rating = parcel.readFloat()
        menus = ArrayList()
        parcel.readTypedList(menus, Menu.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeTypedList(categories)
        parcel.writeFloat(rating)
        parcel.writeTypedList(menus)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }
}