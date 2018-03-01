package com.edcan.chanbobsinse.view.complete

import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 3. 1..
 */
class CompleteModel {
    var menus: ArrayList<Menu> = ArrayList()
        set(menus) {
            menus.forEachIndexed { index, menu ->
                menus[index].isSelected = false
            }
            field = menus
        }
    var restaurant: Restaurant = Restaurant()
    var price: Price = Price()

    fun getSum(): Int = menus.sumBy { it.price }
}