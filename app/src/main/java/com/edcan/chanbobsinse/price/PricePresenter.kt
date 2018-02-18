package com.edcan.chanbobsinse.price

import java.text.DecimalFormat

/**
 * Created by eka on 2018. 2. 18..
 */
class PricePresenter : PriceContract.Presenter {
    override lateinit var view: PriceContract.View

    override fun start() {
        view.initEditText()
    }

    override fun changeText(input: String): String {
        val decimalFormat = DecimalFormat("#,##0")
        val output = decimalFormat.format(input.replace(",", "").toInt())
        if (output.last() == ',')
            output.substring(0, output.lastIndex - 1)
        return output
    }

}