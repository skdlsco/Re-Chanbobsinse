package com.edcan.chanbobsinse.view.complete

import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 3. 1..
 */
class CompletePresenter : CompleteContract.Presenter {
    override lateinit var view: CompleteContract.View
    override var model: CompleteModel = CompleteModel()

    override fun start() {
        view.parsingIntent()

        model.restaurant.let {
            view.run {
                showTitle(it.title)
                showAddress(it.address)
                showPhone(it.phone)
                initCategoryRecyclerView(it.categories)
            }
        }
        view.showSum(model.getSum())

        view.initMenuRecyclerView(model.menus, model.price)
    }

    override fun initData(menus: ArrayList<Menu>, restaurant: Restaurant, price: Price, isRandom: Boolean) {
        model.menus = menus
        model.restaurant = restaurant
        model.price = price
        model.isRandom = isRandom
    }

    override fun gotoMainButtonClick() {
        view.startMainActivity()
    }
}