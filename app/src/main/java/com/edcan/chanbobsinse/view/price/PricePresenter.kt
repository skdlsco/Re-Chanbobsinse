package com.edcan.chanbobsinse.view.price

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 18..
 */
class PricePresenter : PriceContract.Presenter {
    override lateinit var view: PriceContract.View
    override var model: PriceModel = PriceModel()

    override fun start() {
        view.parsingIntent()
        view.initEditText()
    }

    override fun initData(address: String, categories: ArrayList<Category>, latLng: LatLng) {
        model.address = address
        model.categories = categories
        model.latLng = latLng
    }

    override fun updatePrices(min: String, max: String, range: String, func: (Price) -> Unit) {
        model.price.run {
            this.min = min
            this.max = max
            this.range = range
        }

        func.invoke(model.price)
    }

    override fun nextBtnClick() {
        if (model.checkPrice() != "")
            return view.showToast(model.checkPrice())
        view.startSearchingActivity(model.address, model.categories, model.price, model.latLng)
    }
}
