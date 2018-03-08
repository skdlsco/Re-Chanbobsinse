package com.edcan.chanbobsinse.view.main

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import com.edcan.chanbobsinse.BR
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.databinding.ItemMainCategoryBinding
import com.edcan.chanbobsinse.listener.AppBarOffSetChangedListener
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.view.map.MapActivity
import com.edcan.chanbobsinse.view.price.PriceActivity
import com.github.nitrico.lastadapter.LastAdapter
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), MainContract.View {

    val REQUEST_PERMISSION_CODE = 1000
    val REQUEST_MAP_CODE = 3000
    override var presenter: MainContract.Presenter = MainPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.view = this
        presenter.start()

        initAppBar()
        showTrendText()

        nextButton.onClick { presenter.nextButtonClick() }
        contentContainer.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                contentContainer.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val params = contentContainer.layoutParams
                params.height = params.height + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics).toInt()
                contentContainer.layoutParams = params
            }
        })
        mapButton.onClick { presenter.mapButtonClick() }
        requestPermission()
    }

    private fun requestPermission() {
        if (PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED
                && PermissionChecker.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                    REQUEST_PERMISSION_CODE)
        } else {
            presenter.initGpsInfo(this)
        }
    }

    override fun initRecyclerView(categories: ArrayList<Category>) {
        categoryRecyclerView.let {
            it.isNestedScrollingEnabled = false
            it.layoutManager = GridLayoutManager(this, 2)
            LastAdapter(categories, BR.item).apply {
                val adapter = this
                map<Category, ItemMainCategoryBinding>(R.layout.item_main_category) {
                    onClick {
                        presenter.categoryClick(adapter, it.adapterPosition)
                    }
                }
                into(it)
            }
        }
    }

    override fun startPriceActivity(categories: ArrayList<Category>, address: String, latLng: LatLng) {
        startActivity<PriceActivity>("address" to address, "categories" to categories,
                "lat" to latLng.latitude, "lng" to latLng.longitude)
    }

    override fun startMapActivity(latLng: LatLng, address: String) {
        startActivityForResult(Intent(this@MainActivity, MapActivity::class.java).apply {
            putExtra("lat", latLng.latitude)
            putExtra("lng", latLng.longitude)
            putExtra("address", address)
        }, REQUEST_MAP_CODE)
    }

    @SuppressLint("SimpleDateFormat")
    private fun showTrendText() {
        trendDate.text = SimpleDateFormat("yyyy.MM.dd").format(Date())
    }

    override fun updateAddress(address: String) {
        Log.e("asdf", "asdf = $address")
        addressTextView.text = address
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION_CODE -> presenter.permissionResult(grantResults, this)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun showToast(message: String) {
        toast(message)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_MAP_CODE && resultCode == Activity.RESULT_OK && data != null) {
            data.extras.run {
                val lat = getDouble("lat")
                val lng = getDouble("lng")
                val address = getString("address")
                Log.e("main", "onresult adress = $address")
                presenter.mapResult(LatLng(lat, lng), address)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun initAppBar() {
        val searchAnimator = ValueAnimator.ofInt(0, 1).apply {
            duration = 500
            setEvaluator(ArgbEvaluator())
            addUpdateListener {
                val color = it.animatedValue as Int
                searchButton.drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN)
                searchEditText.textColor = color
                searchUnderLine.backgroundColor = color
            }
        }
        val hintAnimator = ValueAnimator.ofInt(0, 1).apply {
            duration = 500
            setEvaluator(ArgbEvaluator())
            addUpdateListener {
                searchEditText.hintTextColor = it.animatedValue as Int
            }
        }
        val trendAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            addUpdateListener {
                trendTextContainer.alpha = it.animatedValue as Float
            }
        }
        appBar.addOnOffsetChangedListener(object : AppBarOffSetChangedListener(0.7) {
            override fun onStateChangeListener(appBarLayout: AppBarLayout?, state: State) {
            }

            override fun onCollapseStartListener(appBarLayout: AppBarLayout?) {
                searchAnimator.run {
                    setIntValues(searchEditText.currentTextColor, Color.WHITE)
                    start()
                }
                hintAnimator.run {
                    setIntValues(searchEditText.currentHintTextColor, Color.parseColor("#deffffff"))
                    start()
                }
                trendAnimator.run {
                    setFloatValues(trendTextContainer.alpha, 0f)
                    start()
                }
            }

            override fun onExpandedStartListener(appBarLayout: AppBarLayout?) {
                searchAnimator.run {
                    setIntValues(searchEditText.currentTextColor, ContextCompat.getColor(this@MainActivity, R.color.colorBlack))
                    start()
                }
                hintAnimator.run {
                    setIntValues(searchEditText.currentHintTextColor, Color.parseColor("#deb9b9b9"))
                    start()
                }
                trendAnimator.run {
                    setFloatValues(trendTextContainer.alpha, 1f)
                    start()
                }
            }

        })
    }
}
