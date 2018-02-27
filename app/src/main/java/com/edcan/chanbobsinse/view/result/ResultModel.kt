package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 2. 20..
 */
class ResultModel {
    var categories = ArrayList<Category>()
    var address = ""
    var restaurants = ArrayList<Restaurant>()

    fun initRestaurants(){
        val data = Restaurant().apply {
            title = "마카나이"
            address = "서울시 용산구 청파로 45길 11"
            phone = "02-711-2016"
            rating = 3.5f
            categories.add(Category("한식", "ㅁㄴㅇㄹ"))
            categories.add(Category("일식", "ㅁㄴㅇㄹ"))
        }
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
    }
}