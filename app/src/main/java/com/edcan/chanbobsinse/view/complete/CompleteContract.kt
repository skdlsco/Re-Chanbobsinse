package com.edcan.chanbobsinse.view.complete

import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.BasePresenter
import com.edcan.chanbobsinse.view.BaseView

/**
 * Created by eka on 2018. 3. 1..
 */
interface CompleteContract {
    interface View : BaseView<Presenter> {
        fun parsingIntent()

        fun showSum(sum: Int)
        fun showTitle(title: String)
        fun showAddress(address: String)
        fun showPhone(phone: String)

        fun initCategoryRecyclerView(categories: ArrayList<Category>)
        fun initMenuRecyclerView(menus: ArrayList<Menu>, price: Price)
        fun startMainActivity()
    }

    interface Presenter : BasePresenter {
        var view: View
        var model: CompleteModel

        fun initData(menus: ArrayList<Menu>, restaurant: Restaurant, price: Price)
        fun gotoMainButtonClick()
    }

}