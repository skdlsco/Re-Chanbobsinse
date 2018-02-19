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

    override fun nextBtnClick() {
        view.startSearchingActivity()
    }
}