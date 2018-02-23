package com.edcan.chanbobsinse.view.searching

import android.os.Bundle
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView

/**
 * Created by eka on 2018. 2. 19..
 */
interface SearchingContract {
    interface View : BaseView<Presenter> {
        fun setImageColor()
        fun startResultActivity(extras: Bundle)
    }

    interface Presenter : BasePresenter {
        var view: View
        fun setIntentExtra(extras: Bundle)
    }
}