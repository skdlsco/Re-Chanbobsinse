package com.edcan.chanbobsinse.price

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.edcan.chanbobsinse.R
import kotlinx.android.synthetic.main.activity_price.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class PriceActivity : AppCompatActivity(), PriceContract.View {

    override lateinit var presenter: PriceContract.Presenter
    @SuppressLint("PrivateResource")
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price)

        setSupportActionBar(toolbar)
        supportActionBar?.run {
            val back = ContextCompat.getDrawable(this@PriceActivity, R.drawable.abc_ic_ab_back_material)
            back?.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_ATOP)
            setHomeAsUpIndicator(back)
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }

        presenter = PricePresenter().apply {
            view = this@PriceActivity
        }

        btnNext.onClick {
            //            startActivity<>()
        }

        minPrice.textChangedListener {
            var result = ""
            onTextChanged { charSequence, _, _, _ ->
                if (charSequence.toString().isNotEmpty() && charSequence.toString() != result) {
                    result = presenter.changeText(charSequence.toString())
                    minPrice.setText(result)
                    minPrice.setSelection(result.length)
                }
            }
        }
        maxPrice.textChangedListener {
            var result = ""
            onTextChanged { charSequence, _, _, _ ->
                if (charSequence.toString().isNotEmpty() && charSequence.toString() != result) {
                    result = presenter.changeText(charSequence.toString())
                    maxPrice.setText(result)
                    maxPrice.setSelection(result.length)
                }
            }
        }
        rangePrice.textChangedListener {
            var result = ""
            onTextChanged { charSequence, _, _, _ ->
                if (charSequence.toString().isNotEmpty() && charSequence.toString() != result) {
                    result = presenter.changeText(charSequence.toString())
                    rangePrice.setText(result)
                    rangePrice.setSelection(result.length)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
