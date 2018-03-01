package com.edcan.chanbobsinse.view.price

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.edcan.chanbobsinse.models.Price

/**
 * Created by eka on 2018. 2. 18..
 */
interface PriceContract {
    interface View : BaseView<Presenter> {
        fun parsingIntent()

        fun initEditText()
        fun showToast(msg: String)

        fun startSearchingActivity(address: String, categories: ArrayList<Category>, price: Price)
    }

    interface Presenter : BasePresenter {
        var view: PriceContract.View
        var model: PriceModel
        fun initData(address: String, categories: ArrayList<Category>)

        fun updatePrices(min: String, max: String, range: String, func: (Price) -> Unit)

        fun nextBtnClick()
    }
}