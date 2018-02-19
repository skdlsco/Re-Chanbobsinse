package com.edcan.chanbobsinse.utils

import java.text.DecimalFormat

/**
 * Created by eka on 2018. 2. 19..
 */
object Util{

    fun formatMoney(input: String): String {
        val decimalFormat = DecimalFormat("#,##0")
        val output = decimalFormat.format(input.replace(",", "").toInt())
        if (output.last() == ',')
            output.substring(0, output.lastIndex - 1)
        return output
    }
}