package com.edcan.chanbobsinse.view.main

import android.content.Context
import android.content.pm.PackageManager
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 21..
 */
class MainPresenter : MainContract.Presenter {
    override lateinit var view: MainContract.View
    override var model: MainModel = MainModel()

    override fun start() {
        view.initRecyclerView(model.categories)
    }

    override fun categoryClick(adapter: LastAdapter, position: Int) {
        model.categories[position].let {
            it.isSelected = !it.isSelected
        }
        adapter.notifyItemChanged(position)
    }

    override fun permissionResult(grantResults: IntArray, context: Context) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            initGpsInfo(context)
        } else {
            view.showToast("주소를 불러올 수 없습니다.")
        }
    }

    override fun initGpsInfo(context: Context) {
        model.initGpsInfo(context)
        model.makeAddress({
            view.updateAddress(model.address)
        })
    }

    override fun nextButtonClick() {
        if (ArrayList(model.getSelectedItem()).isEmpty())
            view.showToast("한 개 이상의 카테고리를 선택해 주세요!")
        else
            view.startPriceActivity(ArrayList(model.getSelectedItem()), model.address, model.latLng)
    }

    override fun mapButtonClick() {
        view.startMapActivity(model.latLng, model.address)
    }

    override fun mapResult(latLng: LatLng, address: String) {
        model.latLng = latLng
        model.address = address
        view.updateAddress(address)
    }
}