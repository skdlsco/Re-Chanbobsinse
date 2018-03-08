package com.edcan.chanbobsinse.view.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.edcan.chanbobsinse.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast


class MapActivity : AppCompatActivity(), MapContract.View, OnMapReadyCallback {

    override var presenter: MapContract.Presenter = MapPresenter()
    private lateinit var mMap: GoogleMap

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            val back = ContextCompat.getDrawable(this@MapActivity, R.drawable.abc_ic_ab_back_material)
            back?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            setHomeAsUpIndicator(back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }

        (supportFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment).getMapAsync(this)
        presenter.view = this
        presenter.start()

        searchButton.onClick {
            presenter.searchButtonClick(addressEditText.text.toString())
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!
        mMap.setOnCameraMoveListener {
            mMap.cameraPosition.target.let {
                presenter.cameraMove(it.latitude, it.longitude)
            }
        }
        mMap.isMyLocationEnabled = true
        presenter.onMapReady()
        checkPermission()
    }

    override fun updateCamera(latLng: LatLng) {
        Log.e("MapActivity", "latLng = ${latLng.latitude}, ${latLng.longitude}")
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
    }

    override fun updateAddress(address: String) {
        addressTextView.text = address
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            toast("위치를 직접 입력해주세요")
        } else {
        }
    }

    override fun parsingIntent() {
        val address = intent.extras.getString("address")
        val lat = intent.extras.getDouble("lat")
        val lng = intent.extras.getDouble("lng")

        presenter.initData(address, LatLng(lat, lng))
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun result(address: String, latLng: LatLng) {
        val intent = Intent().run {
            putExtras(Bundle().apply {
                putString("address", address)
                putDouble("lat", latLng.latitude)
                putDouble("lng", latLng.longitude)
            })
        }
        Log.e("map", "address = $address")
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
        super.onBackPressed()
    }
}
