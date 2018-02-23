package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.edcan.chanbobsinse.models.Price
import com.github.nitrico.lastadapter.LastAdapter

/**
 * Created by eka on 2018. 2. 19..
 */
interface ResultContract {
    interface View : BaseView<Presenter> {
        fun showPriceRange(string: String)
        fun showCoverView()
        fun hideCoverView()
        fun parsingIntent()
    }

    interface Presenter : BasePresenter {
        var view: ResultContract.View
        var model: ResultModel
        var categoryAdapter: LastAdapter
        var restaurantAdapter: LastAdapter
        fun initData(price: Price, address: String, categories: ArrayList<String>)
        fun getRandomMenu()
        fun floatingActionButtonClick(show: Boolean)
    }
}