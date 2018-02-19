package com.edcan.chanbobsinse

import android.os.Handler
import android.util.Log
import android.view.View

/**
 * Created by eka on 2018. 2. 19..
 */
class AlphaAnimation(var view: View, var to: Float, var from: Float, var duration: Long) {
    var isRunning = false

    private val handler = Handler()
    fun start() {
        if (isRunning)
            stop()
        isRunning = true
        (1..50).forEach {
            handler.postDelayed({
                val i = if (to - from > 0) it else 50 - it
                val gap = Math.abs(to - from)
                val alpha = gap / 50 * i
                view.alpha = alpha

                if (it == 100)
                    isRunning = false
                Log.e("asdfasf", "$i")
            }, it * duration / 50)
        }
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
        isRunning = false
    }
}