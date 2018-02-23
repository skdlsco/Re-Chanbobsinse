package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Price
import com.github.nitrico.lastadapter.LastAdapter

/**
 * Created by eka on 2018. 2. 19..
 */
class ResultPresenter : ResultContract.Presenter {

    override var model = ResultModel()
    override lateinit var view: ResultContract.View
    override lateinit var categoryAdapter: LastAdapter
    override lateinit var restaurantAdapter: LastAdapter

    override fun getRandomMenu() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun start() {
        view.parsingIntent()
    }

    override fun initData(price: Price, address: String, categories: ArrayList<String>) {
        model.address = address
        model.categories = categories
        val output = "${price.min}￦ ~ ${price.max}￦ (±${if (price.range != "") price.range else "0"})"
        view.showPriceRange(output)
    }

    override fun floatingActionButtonClick(show: Boolean) {
        if (show)
            view.showCoverView()
        else
            view.hideCoverView()
    }
}