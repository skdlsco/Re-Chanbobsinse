package com.edcan.chanbobsinse.listener

import android.support.design.widget.AppBarLayout
import kotlin.properties.Delegates

/**
 * Created by eka on 2018. 2. 19..
 */
abstract class AppBarOffSetChangedListener(val collapseParallaxMultiplier: Double = 0.5) : AppBarLayout.OnOffsetChangedListener {

    enum class State {
        EXPANDED, COLLAPSE, IDLE
    }

    var appBarLayout: AppBarLayout? = null
    var state by Delegates.observable(State.EXPANDED) { _, old, new ->
        if (old != new)
            onStateChangeListener(appBarLayout, new)
    }
    private var isCollapsing by Delegates.observable(false) { _, old, new ->
        if (old != new) {
            if (new)
                onCollapseStartListener(appBarLayout)
            else
                onExpandedStartListener(appBarLayout)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        this.appBarLayout = appBarLayout
        state = when (Math.abs(verticalOffset)) {
            appBarLayout?.totalScrollRange -> State.COLLAPSE
            0 -> State.EXPANDED
            else -> State.IDLE
        }
        isCollapsing = Math.abs(verticalOffset).toDouble() / appBarLayout!!.totalScrollRange.toDouble() >= collapseParallaxMultiplier
    }

    abstract fun onStateChangeListener(appBarLayout: AppBarLayout?, state: State)
    abstract fun onCollapseStartListener(appBarLayout: AppBarLayout?)
    abstract fun onExpandedStartListener(appBarLayout: AppBarLayout?)
}