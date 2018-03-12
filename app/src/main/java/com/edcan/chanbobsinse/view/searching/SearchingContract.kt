package com.edcan.chanbobsinse.view.searching

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 19..
 */
interface SearchingContract {
    interface View : BaseView<Presenter> {
        fun setImageColor()
        fun startResultActivity(price: Price, categories: ArrayList<Category>, address: String, latLng: LatLng, restaurants: ArrayList<Restaurant>)
        fun parsingIntent()
        fun showToast(msg: String)
    }

    interface Presenter : BasePresenter {
        var view: View
        var model: SearchingModel
        fun initData(price: Price, categories: ArrayList<Category>, address: String, latLng: LatLng)
    }
}