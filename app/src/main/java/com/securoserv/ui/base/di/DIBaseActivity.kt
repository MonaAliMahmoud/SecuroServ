package com.securoserv.ui.base.di

import androidx.appcompat.app.AppCompatActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

abstract class DIBaseActivity: AppCompatActivity() , HasAndroidInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = androidInjector
}