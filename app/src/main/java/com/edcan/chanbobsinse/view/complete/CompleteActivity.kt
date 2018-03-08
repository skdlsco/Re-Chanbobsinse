package com.edcan.chanbobsinse.view.complete

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.edcan.chanbobsinse.BR
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.databinding.ItemMenuBinding
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Menu
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.main.MainActivity
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_complete.*
import kotlinx.android.synthetic.main.item_menu.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class CompleteActivity : AppCompatActivity(), CompleteContract.View {

    override var presenter: CompleteContract.Presenter = CompletePresenter()

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete)


        setSupportActionBar(toolbar)
        supportActionBar?.run {
            val back = ContextCompat.getDrawable(this@CompleteActivity, R.drawable.abc_ic_ab_back_material)
            back?.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
            setHomeAsUpIndicator(back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        presenter.view = this
        presenter.start()

        gotoMainButton.onClick { presenter.gotoMainButtonClick() }
    }

    override fun parsingIntent() {
        val isRandom = intent.extras.getBoolean("random")
        val menus = intent.extras.getParcelableArrayList<Menu>("menus")
        val restaurant = intent.extras.getParcelable<Restaurant>("restaurant")
        val price = intent.extras.getParcelable<Price>("price")
        presenter.initData(menus, restaurant, price, isRandom)
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

    @SuppressLint("SetTextI18n")
    override fun showSum(sum: Int) {
        sumTextView.text = "총합 : ${Price().formatMoney(sum.toString())}￦"
    }

    override fun initCategoryRecyclerView(categories: ArrayList<Category>) {
        categoryRecyclerView.let {
            it.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            it.setOnTouchListener { _, _ -> true }
            LastAdapter(categories, BR.item)
                    .map<Category>(R.layout.item_restaurants_category)
                    .into(it)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun initMenuRecyclerView(menus: ArrayList<Menu>, price: Price) {
        menuRecyclerView.layoutManager = LinearLayoutManager(this)
        LastAdapter(menus, BR.item)
                .map<Menu, ItemMenuBinding>(R.layout.item_menu) {
                    onCreate {
                        it.itemView.priceTextView.text = "${Price().formatMoney(it.binding.item!!.price.toString())}￦"
                        it.itemView.errorPriceTextView.text = "± ${calcMoney(it.binding.item!!.price, price)}￦"
                    }
                }
                .into(menuRecyclerView)
    }

    private fun calcMoney(price: Int, _price: Price): String {
        val min = _price.min.replace(",", "").toInt()
        val max = _price.max.replace(",", "").toInt()
        val money: Int = if (price > max) price - max else if (price < min) min - price else 0

        return Price().formatMoney(money.toString())
    }

    override fun startMainActivity() {
        startActivity<MainActivity>()
        finishAffinity()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
