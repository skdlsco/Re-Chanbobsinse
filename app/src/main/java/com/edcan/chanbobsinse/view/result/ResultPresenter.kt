package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.maps.model.LatLng
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by eka on 2018. 2. 19..
 */
class ResultPresenter : ResultContract.Presenter {
    override var model = ResultModel()
    override lateinit var view: ResultContract.View
    override lateinit var categoryAdapter: LastAdapter
    override lateinit var restaurantAdapter: LastAdapter

    override fun randomButtonClick() {
        val restaurant = model.restaurants[Random().nextInt(model.restaurants.size)]
        val menu = restaurant.menus[Random().nextInt(restaurant.menus.size)]
        val menus = ArrayList<Menu>().apply {
            add(menu)
        }
        view.startCompleteActivity(menus, restaurant, model.price)
    }

    override fun start() {
        view.parsingIntent()
        view.initCategoryRecyclerView(model.categories)
        view.initRestaurantsRecyclerView(model.restaurants)
        view.hideCoverView()
    }

    override fun initData(price: Price, address: String, categories: ArrayList<Category>, latLng: LatLng, restaurants: ArrayList<Restaurant>) {
        model.let {
            it.address = address
            it.categories.addAll(categories)
            it.price = price
            it.latLng = latLng
            it.restaurants = restaurants
        }
        view.updateAddress(address)
        view.showPriceRange(model.getRangeText())
    }

    override fun floatingActionButtonClick(show: Boolean) {
        if (show)
            view.showCoverView()
        else
            view.hideCoverView()
    }

    override fun restaurantsItemClick(restaurant: Restaurant) {
        view.startDetailActivity(restaurant, model.price)
    }
}