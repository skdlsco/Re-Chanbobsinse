package com.edcan.chanbobsinse.result

/**
 * Created by eka on 2018. 2. 19..
 */
class ResultPresenter : ResultContract.Presenter {
    override fun getRandomMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override lateinit var view: ResultContract.View

    override fun start() {
    }

    override fun initPriceRange(min: String, max: String, range: String) {
        //TODO logic
        view.showPriceRange(range)
    }
}