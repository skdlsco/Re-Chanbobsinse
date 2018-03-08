package com.edcan.chanbobsinse.view.searching

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.view.result.ResultActivity
import kotlinx.android.synthetic.main.activity_searching.*
import org.jetbrains.anko.startActivity

class SearchingActivity : AppCompatActivity(), SearchingContract.View {

    override lateinit var presenter: SearchingContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        presenter = SearchingPresenter()
        presenter.view = this

        presenter.start()
        presenter.setIntentExtra(intent.extras)
    }


    override fun setImageColor() {
        image.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange))
    }

    override fun startResultActivity(extras: Bundle) {
        val price = extras.getParcelable<Price>("price")
        val categories = extras.getParcelableArrayList<Category>("categories")
        val address = extras.getString("address")
        val lat = extras.getDouble("lat")
        val lng = extras.getDouble("lng")
        startActivity<ResultActivity>("price" to price, "address" to address,
                "categories" to categories, "lat" to lat, "lng" to lng)
    }
}
