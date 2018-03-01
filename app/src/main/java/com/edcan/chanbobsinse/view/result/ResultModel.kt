package com.edcan.chanbobsinse.view.result

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant

/**
 * Created by eka on 2018. 2. 20..
 */
class ResultModel {
    var categories = ArrayList<Category>()
    var address = ""
    var restaurants = ArrayList<Restaurant>()
    var price = Price()

    fun initRestaurants() {
        val data = Restaurant().apply {
            title = "마카나이"
            address = "서울시 용산구 청파로 45길 11"
            phone = "02-711-2016"
            rating = 3.5f
            categories.run {
                add(Category().apply {
                    title = "한식"
                    discription = "오늘따라 집밥이 그리울 때,\n밖에서 먹는 우리집의 맛"
                })
                add(Category().apply {
                    title = "일식"
                    discription = "초밥, 돈까스, 회, 카레 등등…\n익숙하지만 언제든 먹고싶은 음식"
                })
            }
            menus.add(Menu().apply {
                price = 7500
                title = "돈코츠 라멘"
            })
            menus.add(Menu().apply {
                price = 7500
                title = "규동"
            })
            menus.add(Menu().apply {
                price = 7500
                title = "삼겹살 덮밥"
            })
        }
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
        restaurants.add(data)
    }

    fun getRangeText() = "${price.min}￦ ~ ${price.max}￦ (±${if (price.range != "") price.range else "0"})"
}