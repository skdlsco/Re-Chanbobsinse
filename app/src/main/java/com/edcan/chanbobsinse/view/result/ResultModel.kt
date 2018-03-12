package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.google.android.gms.maps.model.LatLng

/**
 * Created by eka on 2018. 2. 20..
 */
class ResultModel {
    var categories = ArrayList<Category>()
    var address = ""
    var restaurants = ArrayList<Restaurant>()
    var price = Price()
    var latLng = LatLng(0.0, 0.0)

    fun getRangeText() = "${price.min}￦ ~ ${price.max}￦ (±${if (price.range != "") price.range else "0"})"
}