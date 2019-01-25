package com.neiko.smswidget.UI.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.neiko.smswidget.Model.Enum.FubState
import com.neiko.smswidget.R
import com.neiko.smswidget.Session
import kotlinx.android.synthetic.main.activity_base.*


class BaseActivity : AppCompatActivity() {

    private var fubState: FubState = FubState.ADD

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        setSupportActionBar(toolbar)

        Session.instance.setupContext(applicationContext)

        setupActionBarWithNavController(findNavController(R.id.nav_container))
    }

    override fun onSupportNavigateUp(): Boolean {
        hideKeyboard()
        return Navigation.findNavController(findViewById(R.id.nav_container)).navigateUp()
    }


    fun setFubState(state: FubState) {
        findViewById<FloatingActionButton>(R.id.fubButton)?.let { fub ->

            if (getFubState() != state)
                fub.animate().apply {
                    duration = 250
                    scaleX(0f)
                    scaleY(0f)
                    withEndAction {
                        when (state) {
                            FubState.ADD -> {
                                fub.setImageResource(R.drawable.ic_add_white)
                                fub.backgroundTintList =
                                        ColorStateList.valueOf(resources.getColor(R.color.fub_add))
                            }
                            FubState.EDIT -> {
                                fub.setImageResource(R.drawable.ic_edit_white)
                                fub.backgroundTintList =
                                        ColorStateList.valueOf(resources.getColor(R.color.fub_edit))
                            }
                            FubState.DELETE -> {
                                fub.setImageResource(R.drawable.ic_delete_white)
                                fub.backgroundTintList =
                                        ColorStateList.valueOf(resources.getColor(R.color.fub_delete))
                            }
                            FubState.SAVE -> {
                                fub.setImageResource(R.drawable.ic_save_white)
                                fub.backgroundTintList =
                                        ColorStateList.valueOf(resources.getColor(R.color.fub_save))
                            }
                            FubState.SEND -> {
                                fub.setImageResource(R.drawable.ic_send_white)
                                fub.backgroundTintList =
                                        ColorStateList.valueOf(resources.getColor(R.color.fub_send))
                            }
                        }
                        fubState = state

                        fub.animate().apply {
                            duration = 250
                            scaleX(1f)
                            scaleY(1f)
                        }
                    }
                }
        }
    }

    fun getFubState() = fubState

    @SuppressLint("ServiceCast")
    fun hideKeyboard() {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = this.currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
