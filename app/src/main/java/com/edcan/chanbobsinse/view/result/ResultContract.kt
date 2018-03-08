package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 19..
 */
interface ResultContract {
    interface View : BaseView<Presenter> {
        fun parsingIntent()

        fun showPriceRange(string: String)
        fun showCoverView()
        fun hideCoverView()
        fun updateAddress(address: String)

        fun initCategoryRecyclerView(categories: ArrayList<Category>)
        fun initRestaurantsRecyclerView(restaurants: ArrayList<Restaurant>)

        fun startDetailActivity(restaurant: Restaurant, price: Price)
    }

    interface Presenter : BasePresenter {
        var view: ResultContract.View
        var model: ResultModel
        var categoryAdapter: LastAdapter
        var restaurantAdapter: LastAdapter
        fun initData(price: Price, address: String, categories: ArrayList<Category>, latLng: LatLng)

        fun floatingActionButtonClick(show: Boolean)
        fun randomButtonClick()
        fun restaurantsItemClick(restaurant: Restaurant)
    }
}