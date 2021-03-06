package com.edcan.chanbobsinse.view.detail

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.github.nitrico.lastadapter.LastAdapter

/**
 * Created by eka on 2018. 2. 28..
 */
interface DetailContract {
    interface View : BaseView<Presenter> {
        fun parsingIntent()
        fun showTitle(title: String)
        fun showAddress(address: String)
        fun showPhone(phone: String)
        fun showRatingBar(rating: Float)

        fun initCategoryRecyclerView(categories: ArrayList<Category>)
        fun initMenuRecyclerView(menus: ArrayList<Menu>, price: Price)

        fun startCompleteActivity(menus: ArrayList<Menu>, restaurant: Restaurant, price: Price)
    }

    interface Presenter : BasePresenter {
        var view: View
        var model: DetailModel
        fun initData(restaurant: Restaurant, price: Price)
        fun menuClick(adapter: LastAdapter, position: Int)
        fun nextButtonClick()
    }
}