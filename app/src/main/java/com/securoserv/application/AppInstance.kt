package com.securoserv.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.securoserv.di.helper.AppInjector
import com.securoserv.utils.LocalHelper
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class AppInstance : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        AppInjector.init(this)
    }

    override fun androidInjector() = androidInjector

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocalHelper.onAttach(context = baseContext)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(
            LocalHelper.onAttach(
                context = base
            )
        )
    }
}