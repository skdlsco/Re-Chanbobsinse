package com.edcan.chanbobsinse.result

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.edcan.chanbobsinse.AlphaAnimation
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.listener.AppBarOffSetChangedListener
import kotlinx.android.synthetic.main.activity_result.*

class ResultActivity : AppCompatActivity(), ResultContract.View {

    override lateinit var presenter: ResultContract.Presenter

    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        setSupportActionBar(toolbar)
        supportActionBar?.run {
            val back = ContextCompat.getDrawable(this@ResultActivity, R.drawable.abc_ic_ab_back_material)
            back?.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)
            setHomeAsUpIndicator(back)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        presenter = ResultPresenter().apply {
            view = this@ResultActivity
            start()
        }

        appBar.addOnOffsetChangedListener(object : AppBarOffSetChangedListener() {
            val animation = AlphaAnimation(toolbarTitle, 1f, 0f, 500)
            override fun onCollapseStartListener(appBarLayout: AppBarLayout?) {

                animation.run {
                    to = 1f
                    from = toolbarTitle.alpha
                    Log.e("collapse", "$from")
                    start()
                }
            }

            override fun onExpandedStartListener(appBarLayout: AppBarLayout?) {
                animation.run {
                    to = 0f
                    from = toolbarTitle.alpha
                    Log.e("expanded", "$from")
                    start()
                }
            }

            override fun onStateChangeListener(appBarLayout: AppBarLayout?, state: State) {
            }
        })
    }
    override fun showPriceRange(string: String) {
        priceRangeTextView.text = string
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
