package com.edcan.chanbobsinse.searching

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.edcan.chanbobsinse.R
import com.edcan.chanbobsinse.result.ResultActivity
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

        startActivity<ResultActivity>()
    }

    override fun setImageColor() {
        image.setColorFilter(ContextCompat.getColor(this, R.color.colorOrange))
    }
}
