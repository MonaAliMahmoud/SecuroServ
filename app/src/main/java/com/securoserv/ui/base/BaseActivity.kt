package com.securoserv.ui.base

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import com.securoserv.utils.LocalHelper

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocalHelper.onAttach(newBase))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        LocalHelper.onAttach(context = this)
        super.onConfigurationChanged(newConfig)
    }
}