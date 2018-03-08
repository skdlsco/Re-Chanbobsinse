package com.edcan.chanbobsinse.view.map

import android.content.Context
import android.location.Address
import android.location.Location
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 3. 3..
 */
interface MapContract {
    interface View : BaseView<Presenter> {
        fun parsingIntent()
        fun updateCamera(latLng: LatLng)
        fun updateAddress(address: String)
        fun result(address: String, latLng: LatLng)
    }

    interface Presenter : BasePresenter {
        var view: View
        var model: MapModel

        fun initData(address: String, latLng: LatLng)
        fun cameraMove(lat: Double, lng: Double)
        fun searchButtonClick(address: String)
        fun onMapReady()
        fun onBackPressed()
    }
}