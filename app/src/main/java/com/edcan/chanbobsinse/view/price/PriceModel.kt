package com.edcan.chanbobsinse.view.price

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price

/**
 * Created by eka on 2018. 2. 21..
 */
class PriceModel {
    var categories = ArrayList<Category>()
    var address = ""
    var price = Price()

    fun checkPrice(): String = when {
        price.min.isEmpty() || price.max.isEmpty() -> ("최소, 최대 금액를 입력해주세요.")
        Integer.parseInt(price.min.replace(",", "")) > Integer.parseInt(price.max.replace(",", ""))
        -> ("최대 금액을 최소 금액이상으로 입력해주세요.")
        else -> ""
    }
}