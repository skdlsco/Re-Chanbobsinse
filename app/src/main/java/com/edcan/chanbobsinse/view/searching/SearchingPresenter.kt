package com.edcan.chanbobsinse.view.searching

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.google.android.gms.maps.model.LatLng


/**
 * Created by eka on 2018. 2. 19..
 */
class SearchingPresenter : SearchingContract.Presenter {
    override lateinit var view: SearchingContract.View
    override var model: SearchingModel = SearchingModel()

    override fun start() {
        view.setImageColor()
        view.parsingIntent()
    }

    override fun initData(price: Price, categories: ArrayList<Category>, address: String, latLng: LatLng) {
        model.let {
            it.price = price
            it.categories = categories
            it.address = address
            it.latLng = latLng
        }
        startSearching()
    }

    private fun startSearching() {
        model.searching({
            if (it == "success")
                model.run {
                    view.startResultActivity(price, categories, address, latLng, restaurants)
                }
            else
                view.showToast("에러: 다시 시도해 주세요")
        })
    }
}