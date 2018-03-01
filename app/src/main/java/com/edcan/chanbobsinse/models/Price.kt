package com.edcan.chanbobsinse.models

import android.os.Parcel
import android.os.Parcelable
import java.text.DecimalFormat

/**
 * Created by eka on 2018. 2. 21..
 */
class Price() : Parcelable {
    var min: String = ""
        set(value) {
            field = formatMoney(value)
        }
    var max: String = ""
        set(value) {
            field = formatMoney(value)
        }
    var range: String = ""
        set(value) {
            field = formatMoney(value)
        }

    constructor(parcel: Parcel) : this() {
        min = parcel.readString()
        max = parcel.readString()
        range = parcel.readString()
    }

    fun formatMoney(input: String): String {
        var output = ""
        if (input.isNotEmpty()) {
            val decimalFormat = DecimalFormat("#,##0")
            output = decimalFormat.format(input.replace(",", "").toInt())
            if (output.last() == ',')
                output.substring(0, output.lastIndex - 1)
        }
        return output
    }

    fun getArray() = ArrayList<String>().apply {
        add(min)
        add(max)
        add(range)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(min)
        parcel.writeString(max)
        parcel.writeString(range)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Price> {
        override fun createFromParcel(parcel: Parcel): Price {
            return Price(parcel)
        }

        override fun newArray(size: Int): Array<Price?> {
            return arrayOfNulls(size)
        }
    }
}