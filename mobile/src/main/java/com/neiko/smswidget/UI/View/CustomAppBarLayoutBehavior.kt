package com.neiko.smswidget.UI.View

import android.content.Context
import android.text.method.Touch.onTouchEvent
import android.view.MotionEvent
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.View


/*
 * Project: SmsWidget
 * Package: com.neiko.smswidget.UI.View
 * Authod: Neikovich Sergey
 * Date: 20.12.2018
 */
class CustomAppBarLayoutBehavior(context: Context, attrs: AttributeSet) : AppBarLayout.Behavior(context, attrs) {

    var isShouldScroll = false
        private set

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        return isShouldScroll
    }

    override fun onTouchEvent(parent: CoordinatorLayout, child: AppBarLayout, ev: MotionEvent): Boolean {
        return if (isShouldScroll) {
            super.onTouchEvent(parent, child, ev)
        } else {
            false
        }
    }

    fun setScrollBehavior(shouldScroll: Boolean) {
        this.isShouldScroll = shouldScroll
    }
}