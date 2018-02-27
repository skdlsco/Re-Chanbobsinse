package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
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
        fun updateAddress(address: String)
        fun initCategoryRecyclerView(categories: ArrayList<Category>)
        fun initRestaurantsRecyclerView(restaurants: ArrayList<Restaurant>)
    }

    interface Presenter : BasePresenter {
        var view: ResultContract.View
        var model: ResultModel
        var categoryAdapter: LastAdapter
        var restaurantAdapter: LastAdapter
        fun initData(price: Price, address: String, categories: ArrayList<Category>)
        fun getRandomMenu()
        fun floatingActionButtonClick(show: Boolean)
        fun randomButtonClick()
    }
}