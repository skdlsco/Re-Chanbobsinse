package com.edcan.chanbobsinse.searching

import android.widget.ImageView
import com.edcan.chanbobsinse.BasePresenter
import com.edcan.chanbobsinse.BaseView

/**
 * Created by eka on 2018. 2. 19..
 */
interface SearchingContract {
    interface View : BaseView<Presenter> {
        fun setImageColor()
    }

    interface Presenter : BasePresenter {
        var view: View
    }
}