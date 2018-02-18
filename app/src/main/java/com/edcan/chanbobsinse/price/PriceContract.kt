package com.edcan.chanbobsinse.price

import com.edcan.chanbobsinse.BasePresenter
import com.edcan.chanbobsinse.BaseView

/**
 * Created by eka on 2018. 2. 18..
 */
interface PriceContract {
    interface View : BaseView<Presenter> {
        fun initEditText()
    }

    interface Presenter : BasePresenter {
        var view: PriceContract.View
        fun changeText(input: String): String
    }
}