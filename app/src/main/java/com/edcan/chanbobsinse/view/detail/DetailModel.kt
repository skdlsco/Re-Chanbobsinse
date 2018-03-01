package com.edcan.chanbobsinse.view.detail

import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 2. 28..
 */
class DetailModel {
    var restaurant = Restaurant()
    var price = Price()

    fun getSelectedMenu() = restaurant.menus.filter { it.isSelected }
}