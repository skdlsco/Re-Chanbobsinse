package com.edcan.chanbobsinse.models

import java.text.DecimalFormat

/**
 * Created by eka on 2018. 2. 21..
 */
class Price {
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

    fun getArray() = ArrayList<String>().apply {
        add(min)
        add(max)
        add(range)
    }

    companion object {
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
    }
}