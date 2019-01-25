package com.neiko.smswidget

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class BaseActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        // Enables Always-on
        setAmbientEnabled()
    }
}
