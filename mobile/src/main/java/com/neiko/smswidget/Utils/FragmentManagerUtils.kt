package com.neiko.smswidget.Utils

import android.os.Build
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.transition.AutoTransition
import android.transition.TransitionInflater
import android.view.View
import com.neiko.smswidget.R

object FragmentManagerUtils {

    fun replaceFragment(sourceFragment: Fragment, @IdRes containerId: Int, targetFragment: Fragment, tag: String, backStack: Boolean) {
        replaceFragment(sourceFragment.fragmentManager!!, sourceFragment, null, containerId, targetFragment, tag, backStack)
    }

    fun replaceFragment(fragmentManager: FragmentManager, sourceFragment: Fragment, sharedView: View?, @IdRes containerId: Int, targetFragment: Fragment, tag: String, backStack: Boolean) {
        val transaction = fragmentManager
            .beginTransaction()
            .replace(containerId, targetFragment, tag)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (sharedView != null) {
                transaction.addSharedElement(sharedView, sharedView.transitionName)

                sourceFragment.sharedElementEnterTransition = AutoTransition()
                sourceFragment.sharedElementReturnTransition = AutoTransition()
            } else {
                targetFragment.enterTransition = AutoTransition()
                targetFragment.exitTransition = AutoTransition()
            }
        }
        if (backStack) {
            transaction.addToBackStack(null)
        }
        transaction.commitAllowingStateLoss()
    }
}