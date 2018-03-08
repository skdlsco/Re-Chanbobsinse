package com.edcan.chanbobsinse.view.main

import android.content.Context
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 21..
 */
interface MainContract {
    interface View : BaseView<Presenter> {
        fun showToast(message: String)
        fun updateAddress(address: String)

        fun initRecyclerView(categories: ArrayList<Category>)

        fun startPriceActivity(categories: ArrayList<Category>, address: String, latLng: LatLng)
        fun startMapActivity(latLng: LatLng, address: String)
    }

    interface Presenter : BasePresenter {
        var view: View
        var model: MainModel
        fun permissionResult(grantResults: IntArray, context: Context)
        fun initGpsInfo(context: Context)

        fun nextButtonClick()
        fun categoryClick(adapter: LastAdapter, position: Int)
        fun mapButtonClick()
        fun mapResult(latLng: LatLng, address: String)
    }
}