package com.edcan.chanbobsinse.result

import com.edcan.chanbobsinse.BasePresenter
import com.edcan.chanbobsinse.BaseView
import com.edcan.chanbobsinse.listener.AppBarOffSetChangedListener

/**
 * Created by eka on 2018. 2. 19..
 */
interface ResultContract {
    interface View : BaseView<Presenter> {
        fun showPriceRange(string: String)
    }

    interface Presenter : BasePresenter {
        var view: ResultContract.View
        fun initPriceRange(min: String, max: String, range: String)
        fun getRandomMenu()
    }
}