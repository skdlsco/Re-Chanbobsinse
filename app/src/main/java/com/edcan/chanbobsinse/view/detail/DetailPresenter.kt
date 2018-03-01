package com.edcan.chanbobsinse.view.detail

import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.github.nitrico.lastadapter.LastAdapter

/**
 * Created by eka on 2018. 2. 28..
 */
class DetailPresenter : DetailContract.Presenter {
    override lateinit var view: DetailContract.View
    override var model: DetailModel = DetailModel()

    override fun start() {
        view.parsingIntent()

        //show restaurants info
        model.restaurant.let {
            view.run {
                showTitle(it.title)
                showAddress(it.address)
                showPhone(it.phone)
                showRatingBar(it.rating)
            }
        }

        view.initCategoryRecyclerView(model.restaurant.categories)
        view.initMenuRecyclerView(model.restaurant.menus, model.price)
    }

    override fun initData(restaurant: Restaurant, price: Price) {
        model.restaurant = restaurant
        model.price = price
    }

    override fun menuClick(adapter: LastAdapter, position: Int) {
        model.restaurant.menus[position].isSelected = !model.restaurant.menus[position].isSelected
        adapter.notifyItemChanged(position)
    }

    override fun nextButtonClick() {
        view.startCompleteActivity(ArrayList(model.getSelectedMenu()), model.restaurant, model.price)
    }
}