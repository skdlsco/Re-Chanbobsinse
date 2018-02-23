package com.edcan.chanbobsinse.view.result

import android.animation.ValueAnimator
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
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.listener.AppBarOffSetChangedListener
import com.edcan.chanbobsinse.models.Price
import kotlinx.android.synthetic.main.activity_result.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.sdk25.coroutines.onClick

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
        }

        presenter.start()
        initAppBar()

        FAB.onClick {
            FAB.isSelected = !FAB.isSelected
            presenter.floatingActionButtonClick(FAB.isSelected)
        }
    }

    private fun initAppBar() {
        val animator = ValueAnimator.ofFloat().apply {
            duration = 500
            addUpdateListener {
                toolbarTitle.alpha = it.animatedValue as Float
            }
        }
        appBar.addOnOffsetChangedListener(object : AppBarOffSetChangedListener() {
            override fun onCollapseStartListener(appBarLayout: AppBarLayout?) {
                animator.run {
                    animator.setFloatValues(toolbarTitle.alpha, 1f)
                    start()
                }
            }

            override fun onExpandedStartListener(appBarLayout: AppBarLayout?) {
                animator.run {
                    animator.setFloatValues(toolbarTitle.alpha, 0f)
                    start()
                }
            }

            override fun onStateChangeListener(appBarLayout: AppBarLayout?, state: State) {
            }
        })
    }

    override fun parsingIntent() {
        val price = Price().apply {
            val array = intent.extras.getStringArrayList("price")
            min = array[0]
            max = array[1]
            range = array[2]
        }
        val address = intent.extras.getString("address")
        val categories = intent.extras.getStringArrayList("categories")
        presenter.initData(price, address, categories)
    }

    override fun showPriceRange(string: String) {
        priceRangeTextView.text = string
    }

    override fun showCoverView() {
        coverView.visibility = View.VISIBLE
        FAB.background.setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC)
        Log.e("asdf", "asdf ${coverView.height}")
        Log.e("asdf", "asdf ${contentView!!.height}")

    }

    override fun hideCoverView() {
        coverView.visibility = View.GONE
        FAB.background.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange), PorterDuff.Mode.SRC)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
