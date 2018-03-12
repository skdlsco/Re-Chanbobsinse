package com.edcan.chanbobsinse.view.searching

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.models.Restaurant
import com.edcan.chanbobsinse.view.result.ResultActivity
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_searching.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchingActivity : AppCompatActivity(), SearchingContract.View {

    override lateinit var presenter: SearchingContract.Presenter
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        presenter = SearchingPresenter()
        presenter.view = this

        presenter.start()
    }


    override fun parsingIntent() {
        val price = intent.extras.getParcelable<Price>("price")
        val categories = intent.extras.getParcelableArrayList<Category>("categories")
        val address = intent.extras.getString("address")
        val lat = intent.extras.getDouble("lat")
        val lng = intent.extras.getDouble("lng")
        presenter.initData(price, categories, address, LatLng(lat, lng))
    }

    override fun setImageColor() {
        image.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange))
    }

    override fun startResultActivity(price: Price, categories: ArrayList<Category>, address: String, latLng: LatLng, restaurants: ArrayList<Restaurant>) {

        Log.e("adsfasdf", Gson().toJson(restaurants))
        startActivity<ResultActivity>("price" to price, "address" to address,
                "categories" to categories, "lat" to latLng.latitude, "lng" to latLng.longitude, "restaurants" to restaurants)
    }

    override fun showToast(msg: String) {
        toast(msg)
        if (msg == "에러: 다시 시도해 주세요")
            finish()
    }
}
