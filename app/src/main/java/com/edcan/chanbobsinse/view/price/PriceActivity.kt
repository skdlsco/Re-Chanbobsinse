package com.edcan.chanbobsinse.view.price

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.models.Category
import com.edcan.chanbobsinse.models.Price
import com.edcan.chanbobsinse.view.searching.SearchingActivity
import kotlinx.android.synthetic.main.activity_price.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class PriceActivity : AppCompatActivity(), PriceContract.View {

    override lateinit var presenter: PriceContract.Presenter
    var editTexts = ArrayList<EditText>()
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
            setDisplayShowTitleEnabled(false)
        }

        presenter = PricePresenter().apply {
            view = this@PriceActivity
        }
        presenter.start()
        nextButton.onClick {
            presenter.nextBtnClick()
        }
    }

    override fun initEditText() {
        editTexts.add(minPriceEditText)
        editTexts.add(maxPriceEditText)
        editTexts.add(rangePriceEditText)

        editTexts.forEachIndexed { i, edit ->
            edit.addTextChangedListener(MyTextWatcher(i).textWatcher)
        }
    }

    override fun parsingIntent() {
        val address = intent.extras.getString("address")
        val categories: ArrayList<Category> = intent.extras.getParcelableArrayList<Category>("categories")
        presenter.initData(address, categories)
    }

    override fun startSearchingActivity(address: String, categories: ArrayList<Category>, price: Price) {
        startActivity<SearchingActivity>("address" to address, "categories" to categories, "price" to price)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home ->
                onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showToast(msg: String) {
        toast(msg)
    }

    inner class MyTextWatcher(val i: Int) {
        var textWatcher = object : TextWatcher {
            var result = ""
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (charSequence.toString().isNotEmpty() && charSequence.toString() != result) {
                    val texts = ArrayList<String>()
                    editTexts.forEachIndexed { index, edit ->
                        texts.add(if (index == i) charSequence.toString() else edit.text.toString())
                    }
                    presenter.updatePrices(texts[0], texts[1], texts[2],
                            {
                                // 0 = min, 1 = max, 2 = range
                                result = it.getArray()[i]
                                editTexts[i].setText(result)
                                editTexts[i].setSelection(result.length)
                            })
                }
            }
        }
    }
}
