package com.edcan.chanbobsinse.view.searching

import android.os.Bundle


/**
 * Created by eka on 2018. 2. 19..
 */
class SearchingPresenter : SearchingContract.Presenter {
    lateinit var extras: Bundle
    override lateinit var view: SearchingContract.View

    override fun start() {
        view.setImageColor()
    }

    override fun setIntentExtra(extras: Bundle) {
        this.extras = extras
        view.startResultActivity(this.extras)
    }
}