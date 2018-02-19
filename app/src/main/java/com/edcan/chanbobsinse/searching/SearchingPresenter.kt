package com.edcan.chanbobsinse.searching


/**
 * Created by eka on 2018. 2. 19..
 */
class SearchingPresenter : SearchingContract.Presenter {
    override lateinit var view: SearchingContract.View

    override fun start() {
        view.setImageColor()
    }
}