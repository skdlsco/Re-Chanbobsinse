package com.edcan.chanbobsinse.view.detail

import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 2. 28..
 */
class DetailPresenter : DetailContract.Presenter {
    override lateinit var view: DetailContract.View
    override lateinit var model: DetailModel

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
        }l
    }

    override fun initData(restaurant: Restaurant) {
        model.restaurant = restaurant
    }
}