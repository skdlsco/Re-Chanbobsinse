package com.edcan.chanbobsinse.view.map

import android.util.Log
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 3. 4..
 */
class MapPresenter : MapContract.Presenter {
    override lateinit var view: MapContract.View
    override var model: MapModel = MapModel()

    override fun start() {
        view.parsingIntent()
        model.onAddressChanged = {
            view.updateAddress(it)
        }
        model.onLatLngChanged = {
            Log.e("MapPresenter", "latLng = ${it.latitude}, ${it.longitude}")
            view.updateCamera(it)
        }
        model.startAddressUpdate()
    }

    override fun searchButtonClick(address: String) {
        model.addressToLatLng(address)
    }

    override fun cameraMove(lat: Double, lng: Double) {
        model.latLng = LatLng(lat, lng)
    }

    override fun initData(address: String, latLng: LatLng) {
        model.address = address
        model.latLng = latLng
    }

    override fun onMapReady() {
        view.updateAddress(model.address)
        view.updateCamera(model.latLng)
    }

    override fun onBackPressed() {
        view.result(model.address, model.latLng)
    }
}