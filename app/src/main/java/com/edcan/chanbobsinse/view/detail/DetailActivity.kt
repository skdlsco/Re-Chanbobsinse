package com.edcan.chanbobsinse.view.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.edcan.chanbobsinse.BR
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    override var presenter: DetailContract.Presenter = DetailPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter.view = this
        presenter.start()
    }

    override fun parsingIntent() {
//        presenter.initData(restaurant)
    }

    override fun showTitle(title: String) {
        titleTextView.text = title
    }

    override fun showAddress(address: String) {
        addressTextView.text = address
    }

    override fun showPhone(phone: String) {
        phoneTextView.text = phone
    }

    override fun showRatingBar(rating: Float) {
        ratingBar.rating = rating
    }

    override fun initCategoryRecyclerView(categories: ArrayList<Category>) {
        categoryRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.setOnTouchListener { _, _ -> true }
        LastAdapter(categories, BR.item)
                .map<Category>(R.layout.item_detail_category)
                .into(categoryRecyclerView)
    }

    override fun initMenuRecyclerView(menus: ArrayList<Menu>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
