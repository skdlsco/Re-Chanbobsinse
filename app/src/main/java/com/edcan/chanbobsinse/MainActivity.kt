package com.edcan.chanbobsinse

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.edcan.chanbobsinse.price.PriceActivity
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity<PriceActivity>()
    }
}
